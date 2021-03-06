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
import javax.persistence.ManyToOne;
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
@Table(name = "projects")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Projects.findAll", query = "SELECT p FROM Projects p"),
    @NamedQuery(name = "Projects.findByPid", query = "SELECT p FROM Projects p WHERE p.pid = :pid"),
    @NamedQuery(name = "Projects.findByTitle", query = "SELECT p FROM Projects p WHERE p.title = :title"),
    @NamedQuery(name = "Projects.findByStartAt", query = "SELECT p FROM Projects p WHERE p.startAt = :startAt"),
    @NamedQuery(name = "Projects.findByEndAt", query = "SELECT p FROM Projects p WHERE p.endAt = :endAt"),
    @NamedQuery(name = "Projects.findByIsPublic", query = "SELECT p FROM Projects p WHERE p.isPublic = :isPublic"),
    @NamedQuery(name = "Projects.findByCreateAt", query = "SELECT p FROM Projects p WHERE p.createAt = :createAt"),
    @NamedQuery(name = "Projects.findByUpdateAt", query = "SELECT p FROM Projects p WHERE p.updateAt = :updateAt")})

public class Projects implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    //@NotNull
    @Column(name = "pid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 254)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 2147483647)
    @Column(name = "content")
    private String content;
    @Column(name = "start_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startAt;
    @Column(name = "end_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endAt;
    @Column(name = "is_public")
    private Boolean isPublic;
    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;
    @Column(name = "update_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;
    @JoinColumn(name = "admin_id", referencedColumnName = "aid")
    @ManyToOne
    private Admins adminId;
    @JoinColumn(name = "contract_id", referencedColumnName = "cid")
    @OneToOne
    private Contracts contractId;
    @JoinColumn(name = "img_id", referencedColumnName = "img_id")
    @ManyToOne
    private Images imgId;
    @JoinColumn(name = "project_status", referencedColumnName = "lsid")
    @ManyToOne
    private ListStatus projectStatus;

    public Projects() {
    }

    public Projects(Integer pid) {
        this.pid = pid;
    }

    public Projects(Integer pid, String title, String content) {
        this.pid = pid;
        this.title = title;
        this.content = content;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getStartAt() {
        return startAt;
    }

    public void setStartAt(Date startAt) {
        this.startAt = startAt;
    }

    public Date getEndAt() {
        return endAt;
    }

    public void setEndAt(Date endAt) {
        this.endAt = endAt;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
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

    public Admins getAdminId() {
        return adminId;
    }

    public void setAdminId(Admins adminId) {
        this.adminId = adminId;
    }

    public Contracts getContractId() {
        return contractId;
    }

    public void setContractId(Contracts contractId) {
        this.contractId = contractId;
    }

    public Images getImgId() {
        return imgId;
    }

    public void setImgId(Images imgId) {
        this.imgId = imgId;
    }

    public ListStatus getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(ListStatus projectStatus) {
        this.projectStatus = projectStatus;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pid != null ? pid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Projects)) {
            return false;
        }
        Projects other = (Projects) object;
        if ((this.pid == null && other.pid != null) || (this.pid != null && !this.pid.equals(other.pid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Projects[ pid=" + pid + " ]";
    }
    
}
