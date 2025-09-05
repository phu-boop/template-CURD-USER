package phunla2784.edu.vn.website.enums;

public enum PermissionName {
    READ("READ"),
    WRITE("WRITE"),
    UPDATE("UPDATE"),
    DELETE("DELETE"),
    ADMIN("ADMIN"); // Ví dụ quyền dành cho người quản trị

    private final String permissionName;

    // Constructor để gán giá trị cho permission
    PermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    // Phương thức getter để lấy tên quyền
    public String getPermissionName() {
        return permissionName;
    }
}
