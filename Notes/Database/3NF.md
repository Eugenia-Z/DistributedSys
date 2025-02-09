Goal: reduce redundancy & improve consistency

# 第一范式（1NF：确保原子性）

要求：表中的每一列都必须是原子性的，即不可再拆分的。
避免：重复字段、表中嵌套数组或结构化数据。
示例（未满足 1NF）：

```sql
CREATE TABLE Orders (
order_id INT PRIMARY KEY,
customer_name VARCHAR(50),
products TEXT -- 产品列表（如 "iPhone, iPad, MacBook"），不符合原子性
);
```

改进（满足 1NF）：

```sql

CREATE TABLE Order_Items (
order_id INT,
product_name VARCHAR(50),
PRIMARY KEY (order_id, product_name)
);
```

# 第二范式（2NF：消除部分依赖）

要求：在满足 1NF 的基础上，消除部分依赖（非主键列必须完全依赖于主键，而不能只依赖主键的一部分）。
示例（未满足 2NF，部分依赖）：

```sql
CREATE TABLE Order_Items (
    order_id INT,
    product_id INT,
    product_name VARCHAR(50), -- 依赖于 product_id 而不是 order_id
    PRIMARY KEY (order_id, product_id)
);
```

改进（满足 2NF）：

```sql
CREATE TABLE Products (
    product_id INT PRIMARY KEY,
    product_name VARCHAR(50)
);

CREATE TABLE Order_Items (
    order_id INT,
    product_id INT,
    PRIMARY KEY (order_id, product_id),
    FOREIGN KEY (product_id) REFERENCES Products(product_id)
);
```

# 第三范式（3NF：消除传递依赖）

要求：在满足 2NF 的基础上，消除传递依赖（非主键列不能依赖于其他非主键列）。
示例（未满足 3NF，存在传递依赖）：

```sql
CREATE TABLE Employees (
    employee_id INT PRIMARY KEY,
    department_id INT,
    department_name VARCHAR(50) -- 依赖于 department_id，而 department_id 不是主键
);
```

改进（满足 3NF）：

```sql
CREATE TABLE Departments (
    department_id INT PRIMARY KEY,
    department_name VARCHAR(50)
);

CREATE TABLE Employees (
    employee_id INT PRIMARY KEY,
    department_id INT,
    FOREIGN KEY (department_id) REFERENCES Departments(department_id)
);
```
