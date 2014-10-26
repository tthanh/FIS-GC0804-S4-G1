/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.client;

import entities.OrderProductDetails;
import entities.Products;
import helpers.ApplicationHelper;
import helpers.SessionHelper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import models.ProductModel;

/**
 *
 * @author Cu Beo
 */
@ManagedBean
@RequestScoped
public class ProductBean {
    
    @EJB
    private ProductModel productModel;
    
    private int pid;
    private Products currentProduct;
    Map<String, Object> session;
    private int quantity;
    
    public ProductBean() {
    }
    
    public void init() throws IOException {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            ExternalContext ec = ApplicationHelper.getExternalContext();
            if (pid == 0 && !productModel.productExists(pid)) {
                ec.redirect(ec.getRequestContextPath() + "/client/404.xhtml");
            }
        }
    }
    
    public List<Products> getAllProduct() {
        return productModel.getAll();
    }
    
    public int getPid() {
        return pid;
    }
    
    public void setPid(int pid) {
        this.pid = pid;
    }
    
    public Products getCurrentProduct() {
        if (currentProduct == null) {
            currentProduct = productModel.getById(pid);
        }
        return currentProduct;
    }
    
    public void setCurrentProduct(Products currentProduct) {
        this.currentProduct = currentProduct;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
}
