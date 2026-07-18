package API_FrameWork.models;

public class LoginResponse {

    private String token;
    private String empId;
    private String role;
    private String displayName;

    public LoginResponse() {
    }

    // Getters

    public String getToken() {
        return token;
    }

    public String getEmpId() {
        return empId;
    }

    public String getRole() {
        return role;
    }

    public String getDisplayName() {
        return displayName;
    }

    // Setters

    public void setToken(String token) {
        this.token = token;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}