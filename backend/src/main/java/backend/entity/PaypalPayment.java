package backend.entity;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "paypal_payment")
public class PaypalPayment {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "paypal_payment_id", nullable = false)
    private String paypalPaymentId;

    @Column(name = "amount", nullable = false)
    private float amount;

    @Column(name = "currency", nullable = false)
    private String currency;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;

    @Column(name = "updated_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    
    @PrePersist
	protected void onCreate() {
	  createdAt =updatedAt = new Date();
	}

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }

	public PaypalPayment(UUID id, User user, String paypalPaymentId, float amount, String currency, String state,
			Date createdAt, Date deletedAt, Date updatedAt) {
		super();
		this.id = id;
		this.user = user;
		this.paypalPaymentId = paypalPaymentId;
		this.amount = amount;
		this.currency = currency;
		this.state = state;
		this.createdAt = createdAt;
		this.deletedAt = deletedAt;
		this.updatedAt = updatedAt;
	}

	public PaypalPayment() {
		super();
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPaypalPaymentId() {
		return paypalPaymentId;
	}

	public void setPaypalPaymentId(String paypalPaymentId) {
		this.paypalPaymentId = paypalPaymentId;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(Date deletedAt) {
		this.deletedAt = deletedAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
    

}
