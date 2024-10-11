CREATE TABLE tbg_customer (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    cpf VARCHAR(11),
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(11) NOT NULL,
	address VARCHAR(11),
    createDate DATETIME,
    updateDate DATETIME
);