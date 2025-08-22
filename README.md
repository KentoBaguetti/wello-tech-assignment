# wello-tech-assignment

#### gin port: 8081 <br>

create user
`POST/api/user`
POST BODY:
`{ "email" : "kentaro.barne5@gmail.com" }`
<br>
Fetch a page from the DB:
`GET/api/users/page/:id`
<br>
Fetch emails and filter by email:
`GET/api/users/filter/:keyword`
<br>
Fetch the entire DB:
`GET/api/users`

<br><br>

#### spring port: 8080

create user
`POST/api/user`
POST BODY:
`{ "email" : "kentaro.barne5@gmail.com" }`
<br>
Fetch a page from the DB:
`GET/api/user/page/:id`
<br>
Fetch emails and filter by email:
`GET/api/user/email/:keyword`
<br>
Fetch the entire DB:
`GET/api/user`
