# API-documentation

## user

### Token

**route:** /user/token

**Method:** POST

**request:**

private UUID id;
private String phoneNumber;
private String password;
private int balance;
private Date birthday;
private List<UUID> OrdersHistory;
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
    "id": "04056053-5d96-4069-94c3-4b3281ef32a0",
    "phoneNumber": "+79816998765",
    "currentMeetingId": "04056053-5d96-4069-94c3-4b3281ef32a0",
	"ordersHistory": [
        "04056053-5d96-4069-94c3-4b3281ef32a0",
        "04056053-5d96-4069-94c3-4b3281ef32a0",
        "04056053-5d96-4069-94c3-4b3281ef32a0"
    ],
	"rating": 1,
	"age": 1,
	"gender": 1
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
        "id": "04056053-5d96-4069-94c3-4b3281ef32a0",
        "username": "string",
        "displayName": "string",
        "currentMeetingId": "04056053-5d96-4069-94c3-4b3281ef32a0",
        "meetingHistory": [
            "04056053-5d96-4069-94c3-4b3281ef32a0",
            "04056053-5d96-4069-94c3-4b3281ef32a0",
            "04056053-5d96-4069-94c3-4b3281ef32a0"
        ],
        "rating": 1,
        "age": 1,
        "gender": 1
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

**route:** /api/user/update/{userID}

**Method:** PUT

**path Parameters**

* {userID}

**request:**

```json
{
  "phoneNumber": "+79816998765",
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

**route:** /api/user/update/{userID}

**Method:** DELETE

**path Parameters**

* {userID}

**request:** none

**response:** none

## order

### FindByID

**route:** /api/meeting/find_by_id/{meetingID}

**Method:** GET

**path Parameters**

* {meetingID}

**request:** none

response:

```json
{
  "id": "04056053-5d96-4069-94c3-4b3281ef32a0",
  "gatheringPlaceId": "04056053-5d96-4069-94c3-4b3281ef32a0",
  "initiatorsId": "04056053-5d96-4069-94c3-4b3281ef32a0",
  "startTime": "2024-01-30T18:38:25.125Z",
  "endTime": "2024-01-30T18:38:25.125Z",
  "usersQuantity": 2,
  "state": 0
}
```

### Find

**route:** /api/meeting/find

**Method:** GET

**path Parameters**

* gathering place=uuid
* initiator=uuid

**request:** none

**response:**

```json
{
  "04056053-5d96-4069-94c3-4b3281ef32a0": {
    "id": "04056053-5d96-4069-94c3-4b3281ef32a0",
    "gatheringPlaceId": "04056053-5d96-4069-94c3-4b3281ef32a0",
    "initiatorsId": "04056053-5d96-4069-94c3-4b3281ef32a0",
    "startTime": "2024-01-30T18:38:25.125Z",
    "endTime": "2024-01-30T18:38:25.125Z",
    "usersQuantity": 2,
    "state": 0
  },
  "04056053-5d96-4069-94c3-4b3281ef32a0": {
    ...
  }
} // это map[uuid]user
```

### Create

**route:** /api/meeting/create

**Method:** POST

**request:**

```json
{
  "gatheringPlaceId": "04056053-5d96-4069-94c3-4b3281ef32a0",
  "initiatorsId": "04056053-5d96-4069-94c3-4b3281ef32a0",
  "startTime": "2024-01-30T18:38:25.125Z",
  "endTime": "2024-01-30T18:38:25.125Z",
  "usersQuantity": 2,
  "state": 0
}
```

**response:**

```json
{
  "id": "04056053-5d96-4069-94c3-4b3281ef32a0"
}
```

### Update

**route:** /api/meeting/update/{meetingID}

**Method:** PUT

**path Parameters**

* {meetingID}

**request:**

```json
{
  "gatheringPlaceId": "04056053-5d96-4069-94c3-4b3281ef32a0",
  "initiatorsId": "04056053-5d96-4069-94c3-4b3281ef32a0",
  "startTime": "2024-01-30T18:38:25.125Z",
  "endTime": "2024-01-30T18:38:25.125Z",
  "usersQuantity": 2,
  "state": 0
}
```

**response:**

```json
{
  "id": "04056053-5d96-4069-94c3-4b3281ef32a0"
}
```

### Delete

**route:** /api/meeting/update/{meetingID}

**Method:** DELETE

**path Parameters**

* {meetingID}

**request:** none

**response:** none

# gatheringPlace

### FindByID

**route:** /api/gatheringPlace/find_by_id/{gatheringPlaceID}

**Method:** GET

**path Parameters**

* {gatheringPlaceID}

**request:** none

response:

```json
{
  "id": "04056053-5d96-4069-94c3-4b3281ef32a0",
  "address": {
    "country":        "Russia",
    "city":           "Piter",
    "streetName ":    "kronverksky",
    "houseNumber":    "49",
    "BbuildingNumber" 5
  },
  "averagePrice": 1,
  "cuisineType": 0,
  "rating": 1,
  "phoneNumber": "88005553535"
}
```

### Find

**route:** /api/gatheringPlace/find

**Method:** GET

**path Parameters**

* initiator=uuid
* rating=int
* aвdress=(сложна, наверное надо разбить на несколько параметров адреса)
* cuisine_type=int

**request:** none

**response:**

```json
{
  "04056053-5d96-4069-94c3-4b3281ef32a0": {
    "id": "04056053-5d96-4069-94c3-4b3281ef32a0",
    "address": {
      "country":        "Russia",
      "city":           "Piter",
      "streetName ":    "kronverksky",
      "houseNumber":    "49",
      "BbuildingNumber" 5
    },
    "averagePrice": 1,
    "cuisineType": 0,
    "rating": 1,
    "phoneNumber": "88005553535"
  },
  "04056053-5d96-4069-94c3-4b3281ef32a0": {
    ...
  }
} // это map[uuid]user
```

### Create

**route:** /api/gatheringPlace/create

**Method:** POST

**request:**

```json
{
  "address": {
    "country":        "Russia",
    "city":           "Piter",
    "streetName ":    "kronverksky",
    "houseNumber":    "49",
    "BbuildingNumber" 5
  },
  "averagePrice": 1,
  "cuisineType": 0,
  "rating": 1,
  "phoneNumber": "88005553535"
}
```

**response:**

```json
{
  "id": "04056053-5d96-4069-94c3-4b3281ef32a0"
}
```

### Update

**route:** /api/gatheringPlace/update/{gatheringPlaceID}

**Method:** PUT

**path Parameters**

* {gatheringPlaceID}

**request:**

```json
{
  "address": {
    "country":        "Russia",
    "city":           "Piter",
    "streetName ":    "kronverksky",
    "houseNumber":    "49",
    "BbuildingNumber" 5
  },
  "averagePrice": 1,
  "cuisineType": 0,
  "rating": 1,
  "phoneNumber": "88005553535"
}
```

**response:**

```json
{
  "id": "04056053-5d96-4069-94c3-4b3281ef32a0"
}
```

### Delete

**route:** /api/gatheringPlace/update/{gatheringPlaceID}

**Method:** DELETE

**path Parameters**

* {gatheringPlaceID}

**request:** none

**response:** none