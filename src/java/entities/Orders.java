package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "orders")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Orders.findAll", query = "SELECT o FROM Orders o"),
    @NamedQuery(name = "Orders.findByOid", query = "SELECT o FROM Orders o WHERE o.oid = :oid"),
    @NamedQuery(name = "Orders.findByNumber", query = "SELECT o FROM Orders o WHERE o.number = :number"),
    @NamedQuery(name = "Orders.findByLocationName", query = "SELECT o FROM Orders o WHERE o.locationName = :locationName AND o.clientId = :cid"),
    @NamedQuery(name = "Orders.findByLocationAddress", query = "SELECT o FROM Orders o WHERE o.locationAddress = :locationAddress AND o.clientId = :cid"),
    @NamedQuery(name = "Orders.findByStartAt", query = "SELECT o FROM Orders o WHERE o.startAt = :startAt"),
    @NamedQuery(name = "Orders.findByCreateAt", query = "SELECT o FROM Orders o WHERE o.createAt = :createAt"),
    @NamedQuery(name = "Orders.findByUpdateAt", query = "SELECT o FROM Orders o WHERE o.updateAt = :updateAt"),
    @NamedQuery(name = "Orders.orderExists", query = "SELECT COUNT(o.oid) FROM Orders o WHERE o.oid = :oid "),
    @NamedQuery(name = "Orders.findByClientId", query = "SELECT o FROM Orders o WHERE o.clientId = :cid"),
    @NamedQuery(name = "Orders.orderNumberExists", query = "SELECT COUNT(o.oid) FROM Orders o WHERE o.number = :number"),
    @NamedQuery(name = "Orders.findByClientIdAndStatus", query = "SELECT o FROM Orders o WHERE o.clientId = :cid AND o.orderStatus = :orderStatus"),
    @NamedQuery(name = "Orders.searchByNumber", query = "SELECT o FROM Orders o WHERE o.number like :number AND o.clientId = :cid"),
    @NamedQuery(name = "Orders.totalOrder", query = "SELECT COUNT(o.oid) FROM Orders o"),
    @NamedQuery(name = "Orders.findByStatus", query = "SELECT o FROM Orders o WHERE o.orderStatus = :orderStatus"),
    @NamedQuery(name = "Orders.countByStatus", query = "SELECT COUNT(o.oid) FROM Orders o WHERE o.orderStatus = :orderStatus")
})

public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    //@NotNull
    @Column(name = "oid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer oid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 254)
    @Column(name = "number")
    private String number;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 254)
    @Column(name = "location_name")
    private String locationName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "location_address")
    private String locationAddress;
    @Column(name = "start_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startAt;
    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;
    @Column(name = "update_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;
    @OneToOne(mappedBy = "orderId")
    private Contracts contracts;
    @OneToMany(mappedBy = "orderId")
    private List<OrderProductDetails> orderProductDetailsList;
    @JoinColumn(name = "client_id", referencedColumnName = "cid")
    @ManyToOne
    private Clients clientId;
    @JoinColumn(name = "order_status", referencedColumnName = "lsid")
    @ManyToOne
    private ListStatus orderStatus;
    @JoinColumn(name = "payment_type", referencedColumnName = "ptid")
    @ManyToOne
    private PaymentTypes paymentType;

    public Orders() {
    }

    public Orders(Integer oid) {
        this.oid = oid;
    }

    public Orders(Integer oid, String number, String locationName, String locationAddress) {
        this.oid = oid;
        this.number = number;
        this.locationName = locationName;
        this.locationAddress = locationAddress;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
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

    public Date getStartAt() {
        return startAt;
    }

    public void setStartAt(Date startAt) {
        this.startAt = startAt;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Contracts getContracts() {
        return contracts;
    }

    public void setContracts(Contracts contracts) {
        this.contracts = contracts;
    }

    @XmlTransient
    public List<OrderProductDetails> getOrderProductDetailsList() {
        return orderProductDetailsList;
    }

    public void setOrderProductDetailsList(List<OrderProductDetails> orderProductDetailsList) {
        this.orderProductDetailsList = orderProductDetailsList;
    }

    public Clients getClientId() {
        return clientId;
    }

    public void setClientId(Clients clientId) {
        this.clientId = clientId;
    }

    public ListStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(ListStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public PaymentTypes getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentTypes paymentType) {
        this.paymentType = paymentType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (oid != null ? oid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Orders)) {
            return false;
        }
        Orders other = (Orders) object;
        if ((this.oid == null && other.oid != null) || (this.oid != null && !this.oid.equals(other.oid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Orders[ oid=" + oid + " ]";
    }

}
