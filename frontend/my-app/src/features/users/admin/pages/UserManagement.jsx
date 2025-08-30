import React, { useState, useEffect } from "react";
import { userApi } from "../../services/userApi";
import UserForm from "../components/UserForm";
import UserTable from "../components/UserTable";
import UserFilters from "../components/UserFilters";
import UserPagination from "../components/UserPagination";
import Swal from "sweetalert2";

export default function UserManagement() {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [modalOpen, setModalOpen] = useState(false);
  const [editingUser, setEditingUser] = useState(null);
  const [message, setMessage] = useState("");

  // Search/Filter/Sort
  const [searchText, setSearchText] = useState("");
  const [filterRole, setFilterRole] = useState("");
  const [sortField, setSortField] = useState("id");
  const [sortOrder, setSortOrder] = useState("asc");

  // Pagination
  const [currentPage, setCurrentPage] = useState(1);
  const itemsPerPage = 10;

  const showMessage = (msg ) => {
    setMessage(msg);
    setTimeout(() => setMessage(""), 3000);
  };

  const fetchUsers = async () => {
    try {
      setLoading(true);
      const response = await userApi.getAll();
      if (response.data.code === "1000") {
        setUsers(response.data.data);
        showMessage("Tải danh sách user thành công", "success");
      } else {
        showMessage("Lỗi khi tải danh sách user", "error");
      }
    } catch (error) {
      console.error("Error fetching users:", error);
      showMessage("Không thể tải danh sách user", "error");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchUsers();
  }, []);

  const handleAddUser = () => {
    setEditingUser(null);
    setModalOpen(true);
  };

  const handleEditUser = (user) => {
    setEditingUser(user);
    setModalOpen(true);
  };

  const handleViewUser = (user) => {
    console.log("View user:", user);
    showMessage(`Xem chi tiết user: ${user.fullName}`);
  };

  const handleDeleteUser = async (userId) => {
    if (!window.confirm("Bạn có chắc chắn muốn xóa user này?")) return;

    try {
      await userApi.delete(userId);
      showMessage("Xóa user thành công", "success");
      fetchUsers();
    } catch (error) {
      console.error("Error deleting user:", error);
      showMessage("Không thể xóa user", "error");
    }
  };

  const handleSubmitUser = async (userData) => {
    try {
      if (editingUser) {
        await userApi.update(editingUser.id, userData);
        showMessage("Cập nhật user thành công", "success");
      } else {
        await userApi.create(userData);
        showMessage("Thêm user thành công", "success");
      }
      setModalOpen(false);
      fetchUsers();
    } catch (error) {
      console.error("Error saving user:", error);
      showMessage(editingUser ? "Không thể cập nhật user" : "Không thể thêm user", "error");
    }
  };

  // Filter + Search + Sort
  const filteredUsers = users
    .filter((user) => {
      const textMatch =
        user.email.toLowerCase().includes(searchText.toLowerCase()) ||
        user.fullName?.toLowerCase().includes(searchText.toLowerCase()) ||
        user.name?.toLowerCase().includes(searchText.toLowerCase()) ||
        user.phone?.includes(searchText);

      const roleMatch = !filterRole ||
        user.roles.some(role => role.name === filterRole);

      return textMatch && roleMatch;
    })
    .sort((a, b) => {
      let aValue = a[sortField];
      let bValue = b[sortField];

      if (sortField === "createdAt" && aValue) {
        aValue = new Date(aValue);
        bValue = new Date(bValue);
      }

      if (aValue < bValue) return sortOrder === "asc" ? -1 : 1;
      if (aValue > bValue) return sortOrder === "asc" ? 1 : -1;
      return 0;
    });

  // Pagination
  const totalItems = filteredUsers.length;
  const totalPages = Math.ceil(totalItems / itemsPerPage);
  const paginatedUsers = filteredUsers.slice(
    (currentPage - 1) * itemsPerPage,
    currentPage * itemsPerPage
  );

  return (
    <div>
      {/* Header Section */}
      <div className="mb-6">
        <h1 className="text-2xl font-bold text-gray-800">Quản lý Người dùng</h1>
        <p className="mt-1 text-sm text-gray-600">
          Quản lý và theo dõi tất cả người dùng trong hệ thống
        </p>
      </div>

      {/* Message Alert */}
      {message && (
        <div className="mb-4 p-3 bg-blue-100 text-blue-700 rounded-lg">
          {message}
        </div>
      )}

      {/* Filters */}
      <UserFilters
        searchText={searchText}
        onSearchChange={setSearchText}
        filterRole={filterRole}
        onFilterRoleChange={setFilterRole}
        sortField={sortField}
        onSortFieldChange={setSortField}
        sortOrder={sortOrder}
        onSortOrderChange={setSortOrder}
        onAddUser={handleAddUser}
      />

      {/* Table */}
      <UserTable
        users={paginatedUsers}
        onEdit={handleEditUser}
        onView={handleViewUser}
        onDelete={handleDeleteUser}
        isLoading={loading}
      />

      {/* Pagination */}
      <UserPagination
        currentPage={currentPage}
        totalPages={totalPages}
        onPageChange={setCurrentPage}
        itemsPerPage={itemsPerPage}
        totalItems={totalItems}
      />

      {/* Form Modal */}
      <UserForm
        isOpen={modalOpen}
        onClose={() => setModalOpen(false)}
        onSubmit={handleSubmitUser}
        initialData={editingUser}
      />
    </div>
  );
}