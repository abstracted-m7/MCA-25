<?php
/**
 * User Model
 * Handles all user-related database operations
 */
require_once __DIR__ . '/Database.php';

class UserModel {
    private $conn;

    public function __construct() {
        $this->conn = Database::getInstance()->getConnection();
    }

    /**
     * Authenticate a user by user_id and password
     */
    public function authenticate(string $user_id, string $password): array|false {
        $sql = "SELECT * FROM Users WHERE user_id = :user_id AND password = :password AND isavtive = 'Yes'";
        $stmt = $this->conn->prepare($sql);
        $stmt->bindParam(':user_id', $user_id);
        $stmt->bindParam(':password', $password);
        $stmt->execute();
        $result = $stmt->fetchAll();
        return count($result) > 0 ? $result[0] : false;
    }

    /**
     * Get user details by user_id
     */
    public function getUserDetails(string $user_id): array|false {
        $sql = "SELECT * FROM user_details WHERE user_id = :user_id";
        $stmt = $this->conn->prepare($sql);
        $stmt->bindParam(':user_id', $user_id);
        $stmt->execute();
        $result = $stmt->fetchAll();
        return count($result) > 0 ? $result[0] : false;
    }

    /**
     * Insert new user details
     */
    public function insertDetails(string $user_id, string $phone, string $name, string $address): bool {
        $sql = "INSERT INTO user_details (user_id, phone, name, address) 
                VALUES (:user_id, :phone, :name, :address)";
        $stmt = $this->conn->prepare($sql);
        $stmt->bindParam(':user_id', $user_id);
        $stmt->bindParam(':phone', $phone);
        $stmt->bindParam(':name', $name);
        $stmt->bindParam(':address', $address);
        return $stmt->execute();
    }

    /**
     * Update existing user details
     */
    public function updateDetails(int $udid, string $phone, string $name, string $address): bool {
        $sql = "UPDATE user_details 
                SET phone = :phone, name = :name, address = :address 
                WHERE udid = :udid";
        $stmt = $this->conn->prepare($sql);
        $stmt->bindParam(':udid', $udid, PDO::PARAM_INT);
        $stmt->bindParam(':phone', $phone);
        $stmt->bindParam(':name', $name);
        $stmt->bindParam(':address', $address);
        return $stmt->execute();
    }
}
?>
