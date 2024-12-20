-- Cars
CREATE TABLE IF NOT EXISTS cars(
	car_id INT PRIMARY KEY AUTO_INCREMENT,
	model VARCHAR(256) NOT NULL,
	location VARCHAR(256) NOT NULL,
	daily_rate NUMBER NOT NULL,
	is_active INT DEFAULT 1,

	CONSTRAINT chk_location CHECK (location IN ('Sofia', 'Plovdiv', 'Varna', 'Burgas')),
	CONSTRAINT chk_daily_rate CHECK (daily_rate > 0)
);

-- Customers
CREATE TABLE IF NOT EXISTS customers(
	customer_id INT PRIMARY KEY AUTO_INCREMENT,
	first_name VARCHAR(256) NOT NULL,
	last_name VARCHAR(256) NOT NULL,
	address VARCHAR(256) NOT NULL,
	phone VARCHAR(256) NOT NULL,
	age INT NOT NULL,
	has_accidents BOOLEAN NOT NULL,
	is_active INT DEFAULT 1,

	CONSTRAINT chk_age CHECK (age >= 18)
);

-- Offers
CREATE TABLE IF NOT EXISTS offers(
	offer_id INT PRIMARY KEY AUTO_INCREMENT,
	car_id INT NOT NULL,
	customer_id INT NOT NULL,
	start_date DATE NOT NULL,
	days INT NOT NULL,
	total_price NUMBER NOT NULL,
	is_accepted BOOLEAN NOT NULL,
	is_active INT DEFAULT 1,
	FOREIGN KEY (car_id) REFERENCES cars(car_id),
	FOREIGN KEY (customer_id) REFERENCES customers(customer_id),

	CONSTRAINT chk_start_date CHECK (start_date >= CURRENT_DATE),
	CONSTRAINT chk_days CHECK (days > 0)
);