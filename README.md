# wello-tech-assignment

#### gin port: 8081 <br>

create user
`POST/api/user`
POST BODY:
`{ "username" : "Kentaro", "email" : "kentaro.barnes5@gmail.com" }`
<br>
Fetch a page from the DB (offset pagination):
`GET/api/users?offset=0&limit=10`
<br>
Fetch emails and filter by email:
`GET/api/users/filter?email=gmail`
<br>
Fetch the entire DB:
`GET/api/users/all`

<br><br>

#### spring port: 8080

create user
`POST/api/user`
POST BODY:
`{ "username" : "Kentaro", "email" : "kentaro.barnes5@gmail.com" }`
<br>
Fetch a page from the DB (offset pagination):
`GET/api/users?offset=0&limit=10`
<br>
Fetch emails and filter by email:
`GET/api/users/filter?email=gmail`
<br>
Fetch the entire DB:
`GET/api/users/all`
