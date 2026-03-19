<?php
/**
 * Database Model
 * Handles SQLite connection using PDO
 */
class Database {
    private static $instance = null;
    private $conn;

    private function __construct() {
        $dbpath = dirname(__DIR__) . "/mydatabase.db";
        if (file_exists($dbpath)) {
            try {
                $this->conn = new PDO("sqlite:$dbpath");
                $this->conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
                $this->conn->setAttribute(PDO::ATTR_DEFAULT_FETCH_MODE, PDO::FETCH_ASSOC);
            } catch (PDOException $e) {
                die(json_encode(['error' => 'Connection failed: ' . $e->getMessage()]));
            }
        } else {
            die("Database file not found.");
        }
    }

    public static function getInstance(): Database {
        if (self::$instance === null) {
            self::$instance = new Database();
        }
        return self::$instance;
    }

    public function getConnection(): PDO {
        return $this->conn;
    }
}
?>
