# API-documentation

## user

### Token [in process]

**route:** /user/token

**Method:** POST

**request:**

```json
{
  "phoneNumber": "880005553535",
  "password": "1234567890"
}
```

**response:**

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.XbPfbIHMI6arZ3Y922BhjWgQzWXcXNrz0ogtVhfEd2o"
}
```



### FindByID

**route:** /user/{userID}

**Method:** GET

**path Parameters**

* {userID}

**request:**
```json
{
  "id": "04056053-5d96-4069-94c3-4b3281ef32a0"
}
```

response:

```json
{
  "phoneNumber": "+79816998765",
  "password": "firstPassword12345",
  "balance": 0,
  "ordersHistory": [
    "04056053-5d96-4069-94c3-4b3281ef32a0",
    "04056053-5d96-4069-94c3-4b3281ef32a0",
    "04056053-5d96-4069-94c3-4b3281ef32a0"
  ],
  "birthday" : "2012-02-02T08:08:08Z"
}
```

### Find

**route:** /users/find

**Method:** GET

**path Parameters**

* phoneNumber=string
* orderID=UUID

**request:** none

**response:**

```json
{
    "04056053-5d96-4069-94c3-4b3281ef32a0": {
      "phoneNumber": "+79816998765",
      "password": "firstPassword12345",
      "balance": 0,
      "ordersHistory": [
        "04056053-5d96-4069-94c3-4b3281ef32a0",
        "04056053-5d96-4069-94c3-4b3281ef32a0",
        "04056053-5d96-4069-94c3-4b3281ef32a0"
      ],
      "birthday" : "2012-02-02T08:08:08Z"
    },
    "04056053-5d96-4069-94c3-4b3281ef32a0": {
        ...
    }
}
```

### Create

**route:** /users

**Method:** POST

**request:**

```json
{
  "phoneNumber": "+79816998765",
  "password": "firstPassword12345",
  "balance": 0,
  "ordersHistory": [
    "04056053-5d96-4069-94c3-4b3281ef32a0",
    "04056053-5d96-4069-94c3-4b3281ef32a0",
    "04056053-5d96-4069-94c3-4b3281ef32a0"
  ],
  "birthday" : "2012-02-02T08:08:08Z"
}
```

**response:**

```json
{
	"id": "04056053-5d96-4069-94c3-4b3281ef32a0"
}
```

### Update

**route:** /user/{userID}

**Method:** PUT

**path Parameters**

* {userID}

**request:**

```json
{
  "phoneNumber": "+79816998765",
  "password" : "firstPassword12345",
  "balance": 0,
  "ordersHistory": [
    "04056053-5d96-4069-94c3-4b3281ef32a0",
    "04056053-5d96-4069-94c3-4b3281ef32a0",
    "04056053-5d96-4069-94c3-4b3281ef32a0"
  ],
  "birthday" : "2012-02-02T08:08:08Z"
}
```

**response:** none

### Delete

**route:** /user/{userId}

**Method:** DELETE

**path Parameters**

* {userId}

**request:** none

**response:** none

## Order

### FindByID

**route:** /order/{orderId}

**Method:** GET

**path Parameters**

* {orderId}

**request:** none

response:

```json
{
  "id": "04056053-5d96-4069-94c3-4b3281ef32a0",
  "userId": "04056053-5d96-4069-94c3-4b3281ef32a0",
  "shopId": "04056053-5d96-4069-94c3-4b3281ef32a0",
  "date": "2024-02-13",
  "state": "Created",
  "productList": ["04056053-5d96-4069-94c3-4b3281ef32a0"],
  "totalPrice": 1999,
}
```

### Find

**route:** /orders

**Method:** GET

**path Parameters**

* date = date
* userId = uuid

**request:** none

**response:**

```json
{
  "04056053-5d96-4069-94c3-4b3281ef32a0": {
    "id": "04056053-5d96-4069-94c3-4b3281ef32a0",
    "userId": "04056053-5d96-4069-94c3-4b3281ef32a0",
    "shopId": "04056053-5d96-4069-94c3-4b3281ef32a0",
    "date": "2024-02-13",
    "state": "Created",
    "productList": [],
    "totalPrice": 1999,
  },
  "04056053-5d96-4069-94c3-4b3281ef32a0": {
    ...
  }
}
```

### Create

**route:** /orders

**Method:** POST

**request:**

```json
{
  "userId": "04056053-5d96-4069-94c3-4b3281ef32a0",
  "shopId": "04056053-5d96-4069-94c3-4b3281ef32a0",
  "date": "2024-01-30",
  "state": "Created",
  "productList": ["04056053-5d96-4069-94c3-4b3281ef32a0"]
}
```

**response:**

```json
{
  "id": "04056053-5d96-4069-94c3-4b3281ef32a0"
}
```

### Update

**route:** /order/{orderId}

**Method:** PUT

**path Parameters**

* {orderId}

**request:**

```json
 {
  "id": "04056053-5d96-4069-94c3-4b3281ef32a0",
  "userId": "04056053-5d96-4069-94c3-4b3281ef32a0",
  "shopId": "04056053-5d96-4069-94c3-4b3281ef32a0",
  "date": "2024-02-13",
  "state": "Created",
  "productList": [
    "04056053-5d96-4069-94c3-4b3281ef32a0"
  ],
}
```

**response:**

```json
{
  "id": "04056053-5d96-4069-94c3-4b3281ef32a0"
}
```

### Delete

**route:** /order/{orderId}

**Method:** DELETE

**path Parameters**

* {orderId}

**request:** none

**response:** none

# Shop

### FindById

**route:** /shop/{shopId}

**Method:** GET

**path Parameters**

* {shopId}

**request:** none

response:

```json
{
  "id": "04056053-5d96-4069-94c3-4b3281ef32a0",
  "address": {
    "country":        "Russia",
    "city":           "Piter",
    "street":    "kronverksky",
    "houseNumber":    "49",
    "buildingNumber": 5
  }
}
```

### Find

**route:** /shops

**Method:** GET

**path Parameters**

* adress

**request:** none

**response:**

```json
{
  "id": "04056053-5d96-4069-94c3-4b3281ef32a0",
  "address": {
    "country":        "Russia",
    "city":           "Piter",
    "street":    "kronverksky",
    "houseNumber":    "49",
    "buildingNumber": 5
  }
}
```

### Create

**route:** /shops

**Method:** POST

**request:**

```json
{
  "id": "04056053-5d96-4069-94c3-4b3281ef32a0",
  "address": {
    "country":        "Russia",
    "city":           "Piter",
    "street":    "kronverksky",
    "houseNumber":    "49",
    "buildingNumber": 5
  }
}
```

**response:**

```json
{
  "id": "04056053-5d96-4069-94c3-4b3281ef32a0"
}
```

### Update

**route:** /shop/{shopId}

**Method:** PUT

**path Parameters**

* {shopId}

**request:**

```json
{
  "address": {
    "country":        "Russia",
    "city":           "Piter",
    "street ":    "kronverksky",
    "houseNumber":    "49",
    "buildingNumber": 5
  }
}
```

**response:**

```json
{
  "id": "04056053-5d96-4069-94c3-4b3281ef32a0"
}
```

### Delete

**route:** /shop/{shopId}

**Method:** DELETE

**path Parameters**

* {shopId}

**request:** none

**response:** none

## Product

### FindByID

**route:** /product/{productId}

**Method:** GET

**path Parameters**

* {productId}

**request:** none

response:

```json
{
  "type": {
    "id": "04056053-5d96-4069-94c3-4b3281ef32a0",
    "name": "notebook"
  },
  "name": "Yellow notebook",
  "price": "78",
  "features": [
    {
      "featureTypeId": "04056053-5d96-4069-94c3-4b3281ef32a0",
      "value": "yellow"
    }
  ]
}
```

### Find

**route:** /products

**Method:** GET

**path Parameters**

* typeId = uuid
* name = String

**request:** none

**response:**

```json
{
  "04056053-5d96-4069-94c3-4b3281ef32a0": {
    "type": {
      "id": "04056053-5d96-4069-94c3-4b3281ef32a0",
      "name": "notebook"
    },
    "name": "Yellow notebook",
    "price": "78",
    "features": [
      {
        "featureTypeId": "04056053-5d96-4069-94c3-4b3281ef32a0",
        "value": "yellow"
      }
    ]
  },
  "04056053-5d96-4069-94c3-4b3281ef32a0": {
    ...
  }
}
```

### Create

**route:** /products

**Method:** POST

**request:**

```json
{
  "typeId": "04056053-5d96-4069-94c3-4b3281ef32a0",
  "name": "Yellow notebook",
  "price": "78",
  "features": [
    {
    "featureTypeId": "04056053-5d96-4069-94c3-4b3281ef32a0",
    "value": "yellow"
    }
  ]
}
```

**response:**

```json
{
  "id": "04056053-5d96-4069-94c3-4b3281ef32a0"
}
```

### Update

**route:** /product/{productId}

**Method:** PUT

**path Parameters**

* {productId}

**request:**

```json
{
  "typeId": "04056053-5d96-4069-94c3-4b3281ef32a0",
  "name": "Yellow notebook",
  "price": "78",
  "features": [
    {
      "featureTypeId": "04056053-5d96-4069-94c3-4b3281ef32a0",
      "value": "yellow"
    }
  ]
}
```

**response:**

```json
{
  "id": "04056053-5d96-4069-94c3-4b3281ef32a0"
}
```

### Delete

**route:** /product/{productId}

**Method:** DELETE

**path Parameters**

* {productId}

**request:** none

**response:** none

## Methods for creating product types (categories)/feature types (should not be accessible for a usual user)

### Product Type: FindByID

**route:** /productTypes/{productTypeId}

**Method:** GET

**path Parameters**

* {productTypeId}

**request:** none

response:

```json
{
  "id": "04056053-5d96-4069-94c3-4b3281ef32a0",
  "name": "notebook"
}
```

### Product Type: FindByName

**route:** /productTypes

**Method:** GET

**path Parameters**

* name = String

**request:** none

**response:**

```json
{
  "id": "04056053-5d96-4069-94c3-4b3281ef32a0",
  "name": "notebook"
}
```

### Product Type: Create

**route:** /productTypes

**Method:** POST

**request:**

```json
{
  "name": "notebook"
}
```

**response:**

```json
{
  "id": "04056053-5d96-4069-94c3-4b3281ef32a0"
}
```

### Product Type: Update

**route:** /productType/{productTypeId}

**Method:** PUT

**path Parameters**

* {productTypeId}

**request:**

```json
{
  "id": "04056053-5d96-4069-94c3-4b3281ef32a0",
  "name": "notebook"
}
```

**response:**

```json
{
  "id": "04056053-5d96-4069-94c3-4b3281ef32a0"
}
```

### Product Type: Delete

**route:** /productType/{productTypeId}

**Method:** DELETE

**path Parameters**

* {productTypeId}

**request:** none

**response:** none

### Feature Type: FindByID

**route:** /featureTypes/{featureTypeId}

**Method:** GET

**path Parameters**

* {featureTypeId}

**request:** none

response:

```json
{
  "id": "04056053-5d96-4069-94c3-4b3281ef32a0",
  "name": "color"
}
```

### Feature Type: FindByName

**route:** /featureTypes

**Method:** GET

**path Parameters**

* name = String

**request:** none

**response:**

```json
{
  "id": "04056053-5d96-4069-94c3-4b3281ef32a0",
  "name": "color"
}
```

### Feature Type: Create

**route:** /featureTypes

**Method:** POST

**request:**

```json
{
  "name": "color"
}
```

**response:**

```json
{
  "id": "04056053-5d96-4069-94c3-4b3281ef32a0"
}
```

### Feature Type: Update

**route:** /featureType/{featureTypeId}

**Method:** PUT

**path Parameters**

* {productTypeId}

**request:**

```json
{
  "id": "04056053-5d96-4069-94c3-4b3281ef32a0",
  "name": "color"
}
```

**response:**

```json
{
  "id": "04056053-5d96-4069-94c3-4b3281ef32a0"
}
```

### Feature Type: Delete

**route:** /featureType/{featureTypeId}

**Method:** DELETE

**path Parameters**

* {featureTypeId}

**request:** none

**response:** none
