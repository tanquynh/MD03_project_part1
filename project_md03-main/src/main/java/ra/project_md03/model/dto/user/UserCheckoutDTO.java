package ra.project_md03.model.dto.user;

import javax.validation.constraints.NotEmpty;

public class UserCheckoutDTO {
    private int userId;
    @NotEmpty(message = "Please fill username!")
    private String username;
    @NotEmpty(message = "Please fill address!")
    private String address;
    @NotEmpty(message = "Please fill phone!")
    private String phone;
    private String note;
    private String email;
    @NotEmpty(message = "Please choose username!")
    private String payment;

    public UserCheckoutDTO() {
    }

    public UserCheckoutDTO(int userId, String username, String address, String phone, String note, String email, String payment) {
        this.userId = userId;
        this.username = username;
        this.address = address;
        this.phone = phone;
        this.note = note;
        this.email = email;
        this.payment = payment;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }
}
