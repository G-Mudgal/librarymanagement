# librarymanagement
Simple Library Management System developed using Spring Boot and Spring Secrity

**Important: Swagger support is not yet available for spring 3**

H2 In Memory Database has been used and users are also stored within memory only

Password encoder used: NoOpPasswordEncoder(since it's a demo project)

Available Users:
- Username: admin, Password: admin
- Username: staff, Password: staff
- Username: user, Password: user

Endpoints:
- /login (POST): Accessibel to anyone. Accepts JSON object {"userName": "", "password": ""} and returns a JWT
- /library/catalogue (GET): Accessible to authenticated users only. Takes sortField, offset, pageSize as optional request parameters
- /library (GET): Accessible to authenticated users only. Takes bookId, bookName, bookAuthor, bookCatalogue, bookPublisher as optinal request parameters
- /library (POST): Accessible to Admin and Staff user only. Takes object of Book as request body and saves this book object to DB
- /library (DELETE): Accessible to Admin and Staff user only. Takes bookId, bookName, bookAuthor, bookCatalogue, bookPublisher as optinal request parameters and deletes the book based on these parameters

Book Object:
{
    "bookId": 1 (No need to specify this as this is auto generated)
    "bookName": "",
    "bookAuthor": "",
    "bookCatalogue": 1,
    "bookPrice": 1.00,
    "bookPublisher": ""
}
