package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "product_informations")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProductInformations.findAll", query = "SELECT p FROM ProductInformations p"),
    @NamedQuery(name = "ProductInformations.findByPinfoid", query = "SELECT p FROM ProductInformations p WHERE p.pinfoid = :pinfoid"),
    @NamedQuery(name = "ProductInformations.findByManufacturer", query = "SELECT p FROM ProductInformations p WHERE p.manufacturer = :manufacturer"),
    @NamedQuery(name = "ProductInformations.findByProducedIn", query = "SELECT p FROM ProductInformations p WHERE p.producedIn = :producedIn"),
    @NamedQuery(name = "ProductInformations.findBySize", query = "SELECT p FROM ProductInformations p WHERE p.size = :size"),
    @NamedQuery(name = "ProductInformations.findByELoad", query = "SELECT p FROM ProductInformations p WHERE p.eLoad = :eLoad"),
    @NamedQuery(name = "ProductInformations.findBySpeed", query = "SELECT p FROM ProductInformations p WHERE p.speed = :speed"),
    @NamedQuery(name = "ProductInformations.findByFeature1", query = "SELECT p FROM ProductInformations p WHERE p.feature1 = :feature1"),
    @NamedQuery(name = "ProductInformations.findByFeature2", query = "SELECT p FROM ProductInformations p WHERE p.feature2 = :feature2"),
    @NamedQuery(name = "ProductInformations.findByFeature3", query = "SELECT p FROM ProductInformations p WHERE p.feature3 = :feature3"),
    @NamedQuery(name = "ProductInformations.findByImagePath", query = "SELECT p FROM ProductInformations p WHERE p.imagePath = :imagePath"),
    @NamedQuery(name = "ProductInformations.findByCreateAt", query = "SELECT p FROM ProductInformations p WHERE p.createAt = :createAt"),
    @NamedQuery(name = "ProductInformations.findByUpdateAt", query = "SELECT p FROM ProductInformations p WHERE p.updateAt = :updateAt")})

public class ProductInformations implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    //@NotNull
    @Column(name = "pinfoid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pinfoid;
    @Size(max = 254)
    @Column(name = "manufacturer")
    private String manufacturer;
    @Size(max = 254)
    @Column(name = "produced_in")
    private String producedIn;
    @Size(max = 254)
    @Column(name = "size")
    private String size;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "informations")
    private String informations;
    @Size(max = 254)
    @Column(name = "e_load")
    private String eLoad;
    @Size(max = 254)
    @Column(name = "speed")
    private String speed;
    @Size(max = 2147483647)
    @Column(name = "feature_1")
    private String feature1;
    @Size(max = 2147483647)
    @Column(name = "feature_2")
    private String feature2;
    @Size(max = 2147483647)
    @Column(name = "feature_3")
    private String feature3;
    @Size(max = 2147483647)
    @Column(name = "image_path")
    private String imagePath;
    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;
    @Column(name = "update_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;
    @JoinColumn(name = "product_id", referencedColumnName = "pid")
    @OneToOne
    private Products productId;

    public ProductInformations() {
    }

    public ProductInformations(Integer pinfoid) {
        this.pinfoid = pinfoid;
    }

    public Integer getPinfoid() {
        return pinfoid;
    }

    public void setPinfoid(Integer pinfoid) {
        this.pinfoid = pinfoid;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getProducedIn() {
        return producedIn;
    }

    public void setProducedIn(String producedIn) {
        this.producedIn = producedIn;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getInformations() {
        return informations;
    }

    public void setInformations(String informations) {
        this.informations = informations;
    }

    public String getELoad() {
        return eLoad;
    }

    public void setELoad(String eLoad) {
        this.eLoad = eLoad;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getFeature1() {
        return feature1;
    }

    public void setFeature1(String feature1) {
        this.feature1 = feature1;
    }

    public String getFeature2() {
        return feature2;
    }

    public void setFeature2(String feature2) {
        this.feature2 = feature2;
    }

    public String getFeature3() {
        return feature3;
    }

    public void setFeature3(String feature3) {
        this.feature3 = feature3;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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

    public Products getProductId() {
        return productId;
    }

    public void setProductId(Products productId) {
        this.productId = productId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pinfoid != null ? pinfoid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductInformations)) {
            return false;
        }
        ProductInformations other = (ProductInformations) object;
        if ((this.pinfoid == null && other.pinfoid != null) || (this.pinfoid != null && !this.pinfoid.equals(other.pinfoid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.ProductInformations[ pinfoid=" + pinfoid + " ]";
    }

}