package beans.client.order;

import entities.Clients;
import entities.OrderProductDetails;
import entities.Orders;
import entities.PaymentTypes;
import entities.Products;
import helpers.ApplicationHelper;
import helpers.PersistenceHelper;
import helpers.SessionHelper;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import models.OrderModel;
import models.OrderProductDetailModel;
import models.PaymentTypeModel;
import models.ProductModel;

@ManagedBean
@RequestScoped
public class OrderEditBean {

    @EJB
    private OrderProductDetailModel orderProductDetailModel;

    @EJB
    private PaymentTypeModel paymentTypeModel;
    @EJB
    private ProductModel productModel;
    @EJB
    private OrderModel orderModel;
    private Orders order;
    private String number;
    private String locationName;
    private String locationAddress;
    private int paymentId;
    private Map<String, Object> session = SessionHelper.getSessionMap();
    private Products currentProduct;
    private HtmlDataTable edit_products;
    private int quantity;
    private int floor;
    private BigDecimal heightOfFloor;

    public OrderEditBean() {
    }

    public void init() {
        Clients client = SessionHelper.getCurrentClient();
        if (!FacesContext.getCurrentInstance().isPostback()) {
            if (!orderModel.orderExists(number)) {
                ApplicationHelper.redirect("/404.xhtml", false);
            }

            //get order
            order = orderModel.getByNumber(number);
            if(!Objects.equals(order.getClientId().getCid(), client.getCid())){
                ApplicationHelper.redirect("/403.xhtml", true);
            }
            if (order.getOrderStatus().getLsid() > 1) {
                ApplicationHelper.addMessage("Order not in pending status! You can't edit now!");
                ApplicationHelper.redirect("/client/order/details.xhtml?number=" + number, true);
            }
            locationName = order.getLocationName();
            locationAddress = order.getLocationAddress();
            paymentId = order.getPaymentType().getPtid();

            session.put("edit_number", number);

            boolean reload = false;
            if (session.get("edit_products") == null) {
                reload = true;

            } else {
                List<OrderProductDetails> opds = (List<OrderProductDetails>) session.get("edit_products");
                if (opds.size() > 0) {
                    if (!Objects.equals(opds.get(0).getOrderId().getOid(), order.getOid())) {
                        reload = true;
                    }
                } else {
                    reload = true;
                }
            }
            if (reload) {
                session.put("edit_products", order.getOrderProductDetailsList());
            }
        }
    }

    public void updateInfo() {
        if (!locationName.isEmpty() && !locationAddress.isEmpty()) {
            order = orderModel.getByNumber(number);
            order.setLocationName(locationName);
            order.setLocationAddress(locationAddress);
            order.setPaymentType(new PaymentTypes(paymentId));
            order.setUpdateAt(PersistenceHelper.getCurrentTime());

            boolean result = orderModel.update(order);
            if (result) {
                ApplicationHelper.addMessage("Order updated!");
            } else {
                ApplicationHelper.addMessage("Failed to update order!");
            }
            ApplicationHelper.redirect("/client/order/details.xhtml?number=" + number, true);
        } else {
            ApplicationHelper.addMessage("Please fill all blank field!");
            ApplicationHelper.redirect("/client/order/edit_info.xhtml?number=" + number, true);
        }
    }

    public List<OrderProductDetails> getListOrderProducts() {
        return (List<OrderProductDetails>) session.get("edit_products");
    }

    public List<PaymentTypes> getListPaymentTypes() {
        return paymentTypeModel.getAll();
    }

    public Products getCurrentProduct(int pid) {
        if (currentProduct == null) {
            currentProduct = productModel.getById(pid);
        } else if (currentProduct.getPid() != pid) {
            currentProduct = productModel.getById(pid);
        }

        return currentProduct;
    }

    public int getTotalSelectedProducts() {
        int total = 0;
        List<OrderProductDetails> opds = (List<OrderProductDetails>) session.get("edit_products");
        total = opds.size();
        return total;
    }

    public BigDecimal getTotalSelectedProductsPrice() {
        session = SessionHelper.getSessionMap();
        BigDecimal totalPrice = BigDecimal.ZERO;
        List<OrderProductDetails> opds = (List<OrderProductDetails>) session.get("edit_products");
        List<Products> products = productModel.getAll();
        for (OrderProductDetails opd : opds) {
            for (Products p : products) {
                if (Objects.equals(opd.getProductId().getPid(), p.getPid())) {
                    //add total product price
                    totalPrice = totalPrice.add(p.getPrice().multiply(new BigDecimal(opd.getQuantity())));
                    //add total contruction price
                    BigDecimal totalHeight = opd.getHeightOfFloor().multiply(new BigDecimal(opd.getFloors()));
                    totalPrice = totalPrice.add(p.getConstructionPrice().multiply(totalHeight).setScale(0, RoundingMode.HALF_UP));
                }
            }
        }

        return totalPrice;
    }

    public void addSelectedProduct() {
        String string_pid = ApplicationHelper.getRequestParameterMap().get("pid");
        int pid = 0;
        if (ApplicationHelper.isInteger(string_pid)) {
            pid = Integer.parseInt(string_pid);
        } else {
            ApplicationHelper.addMessage("Wrong Product ID!");
            ApplicationHelper.redirect("/404.xhtml", true);
        }
        List<String> list_msg = new ArrayList<>();
        session = SessionHelper.getSessionMap();
        if (session.get("edit_products") != null && session.get("edit_number") != null) {

            String edit_number = session.get("edit_number").toString();

            Orders current_order = orderModel.getByNumber(edit_number); //get edit order

            List<OrderProductDetails> opds = (List<OrderProductDetails>) session.get("edit_products");

            boolean exists = false;
            for (OrderProductDetails opd : opds) {
                if (opd.getProductId().getPid() == pid) {
                    opd.setQuantity(quantity);
                    opd.setFloors(floor);
                    opd.setHeightOfFloor(heightOfFloor);
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                OrderProductDetails opd = new OrderProductDetails();
                opd.setOrderId(current_order);
                opd.setProductId(new Products(pid));
                opd.setQuantity(quantity);
                opd.setFloors(floor);
                opd.setHeightOfFloor(heightOfFloor);
                opds.add(opd);
            }

            session.put("edit_products", opds);
            ApplicationHelper.addMessage("Product added!");

            ApplicationHelper.redirect("/client/order/edit_products.xhtml?number=" + edit_number, true);

        } else {
            ApplicationHelper.addMessage("You are not in update mode!");
            ApplicationHelper.redirect("/product/view.xhtml?pid=" + pid + "&mode=update", true);
        }
    }

    public void updateSelectedProduct() {

        OrderProductDetails opd = (OrderProductDetails) edit_products.getRowData();
        if (opd.getQuantity() > 0 && opd.getQuantity() < 10) {
            List<OrderProductDetails> opds = (List<OrderProductDetails>) session.get("edit_products");
            for (OrderProductDetails o : opds) {
                if (Objects.equals(o.getProductId().getPid(), opd.getProductId().getPid())) {
                    o.setQuantity(opd.getQuantity());
                    o.setFloors(opd.getFloors());
                    o.setHeightOfFloor(opd.getHeightOfFloor());
                }
            }
        } else {
            ApplicationHelper.addMessage("Quantity min 1 and max 10");
        }

        ApplicationHelper.redirect("/client/order/edit_products.xhtml?number=" + number, true);
    }

    public void removeSelectedProduct() {
        int index = -1;
        OrderProductDetails opd = (OrderProductDetails) edit_products.getRowData();
        List<OrderProductDetails> opds = (List<OrderProductDetails>) session.get("edit_products");
        if (opds.size() == 1) {
            ApplicationHelper.addMessage("Order need one or more products!");
            ApplicationHelper.redirect("/client/order/edit_products.xhtml?number=" + number, true);
        }

        for (OrderProductDetails o : opds) {
            if (Objects.equals(o.getProductId().getPid(), opd.getProductId().getPid())) {
                index = opds.indexOf(o);
            }
        }

        if (index >= 0) {
            opds.remove(index);
            ApplicationHelper.addMessage("Product removed!");
        }
        ApplicationHelper.redirect("/client/order/edit_products.xhtml?number=" + number, true);
    }

    public void updateProducts() {
        session = SessionHelper.getSessionMap(); //get session

        Orders current_order = orderModel.getByNumber(number); // get edit order

        List<OrderProductDetails> change = new ArrayList<>();
        List<OrderProductDetails> remove = new ArrayList<>();
        List<OrderProductDetails> add = new ArrayList<>();

        //find change
        List<OrderProductDetails> current_opds = (List<OrderProductDetails>) session.get("edit_products");
        List<OrderProductDetails> order_opds = current_order.getOrderProductDetailsList();
        for (OrderProductDetails co : current_opds) {
            boolean exists = false; // vòng ngoài có, vong trong k có
            for (OrderProductDetails oo : order_opds) {
                if (Objects.equals(oo.getOpdid(), co.getOpdid())) {
                    oo.setQuantity(co.getQuantity());
                    oo.setFloors(co.getFloors());
                    oo.setHeightOfFloor(co.getHeightOfFloor());
                    change.add(oo);
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                add.add(co);
            }
        }

        for (OrderProductDetails oo : order_opds) {
            boolean exists = false;
            for (OrderProductDetails co : current_opds) {
                if (Objects.equals(co.getOpdid(), oo.getOpdid())) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                remove.add(oo);
            }
        }

        boolean result = true;
        if (!orderProductDetailModel.createList(add)) {
            ApplicationHelper.addMessage("Failed to add new product to order!");
            result = false;
        }
        if (!orderProductDetailModel.updateList(change)) {
            ApplicationHelper.addMessage("Failed to update exists product in order!");
            result = false;
        }
        if (!orderProductDetailModel.removeList(remove)) {
            ApplicationHelper.addMessage("Failed to remove product in order!");
            result = false;
        }
        if (result) {
            ApplicationHelper.addMessage("Order products updated!");
        }

        session.remove("edit_products");

        //update order product details for current order
        List<OrderProductDetails> updated_opds = orderProductDetailModel.getByOrderId(current_order);
        current_order.setOrderProductDetailsList(updated_opds);
        ApplicationHelper.redirect("/client/order/details.xhtml?number=" + number, true);
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public HtmlDataTable getEdit_products() {
        return edit_products;
    }

    public void setEdit_products(HtmlDataTable edit_products) {
        this.edit_products = edit_products;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public BigDecimal getHeightOfFloor() {
        return heightOfFloor;
    }

    public void setHeightOfFloor(BigDecimal heightOfFloor) {
        this.heightOfFloor = heightOfFloor;
    }

}
