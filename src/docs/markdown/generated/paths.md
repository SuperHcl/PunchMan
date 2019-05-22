
<a name="paths"></a>
## Paths

<a name="helloworldusingget"></a>
### helloWorld
```
GET /hello/test
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**access_token**  <br>*required*|user access token|string|
|**Header**|**timestamp**  <br>*optional*|access timestamp|integer (int32)|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|string|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Produces

* `*/*`


#### Tags

* hello-controller


#### Security

|Type|Name|Scopes|
|---|---|---|
|**apiKey**|**[Authorization](#authorization)**|global|


<a name="createusingpost"></a>
### 创建用户
```
POST /user/users
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**access_token**  <br>*required*|user access token|string|
|**Header**|**timestamp**  <br>*optional*|access timestamp|integer (int32)|
|**Body**|**user**  <br>*required*|user|[base user information](#base-user-information)|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[base user information](#base-user-information)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* 用户管理


#### Security

|Type|Name|Scopes|
|---|---|---|
|**apiKey**|**[Authorization](#authorization)**|global|


<a name="listusingget"></a>
### 用户列表
```
GET /user/users
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**access_token**  <br>*required*|user access token|string|
|**Header**|**timestamp**  <br>*optional*|access timestamp|integer (int32)|
|**Query**|**pageIndex**  <br>*optional*|查看第几页|integer (int32)|
|**Query**|**size**  <br>*optional*|每页多少条|integer (int32)|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|< [base user information](#base-user-information) > array|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Produces

* `*/*`


#### Tags

* 用户管理


#### Security

|Type|Name|Scopes|
|---|---|---|
|**apiKey**|**[Authorization](#authorization)**|global|


<a name="findbyidusingget"></a>
### 用户详情
```
GET /user/users/{id}
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**access_token**  <br>*required*|user access token|string|
|**Header**|**timestamp**  <br>*optional*|access timestamp|integer (int32)|
|**Path**|**id**  <br>*required*|id|integer (int64)|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[base user information](#base-user-information)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Produces

* `*/*`


#### Tags

* 用户管理


#### Security

|Type|Name|Scopes|
|---|---|---|
|**apiKey**|**[Authorization](#authorization)**|global|



