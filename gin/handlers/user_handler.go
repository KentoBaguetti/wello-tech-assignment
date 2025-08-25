package handlers

import (
	"net/http"
	"strconv"
	"strings"

	"github.com/KentoBaguetti/wello-tech-assignment/db"
	"github.com/KentoBaguetti/wello-tech-assignment/models"
	"github.com/gin-gonic/gin"
)

type UserHandler struct {
	db *db.UserDB
}

// json param struct for when teh POSt response is parsed
type UserData struct {
	Username string `json:"username"`
	Email string `json:"email" binding:"required"`
}

// 'constructor'
func NewUserHandler(database *db.UserDB)  *UserHandler {
	temp := UserHandler{db: database}
	return &temp
}

func (uh *UserHandler) CreateUser(c *gin.Context) {
	var userData UserData

	// check if the email value is null
	if err := c.ShouldBindJSON(&userData); err != nil {
		 c.JSON(400, gin.H{"msg": "Error creating user"})
        return
	}

	// check if the email value exists
	if userData.Email == "" {
		c.JSON(400, gin.H{"msg": "Please enter an email"})
        return
	}

	// check if the email is valid
	if !strings.Contains(userData.Email, "@") {
		c.JSON(400, gin.H{"msg": "Please enter a valid email address"})
        return
	}

	newUser := models.User{Username: userData.Username, Email: userData.Email}

	uh.db.AddUser(newUser)

	c.JSON(http.StatusOK, gin.H{
		"msg" : "Successfully added user",
		"TotalNumberOfUsers" : uh.db.GetNumberOfUsers(),
		"username": userData.Username,
		"email" : userData.Email,
	})

}

func (uh *UserHandler) Paginate(c *gin.Context) {
	
	offset := c.DefaultQuery("offset", "0")
	limit := c.DefaultQuery("limit", "10")

	offsetInt, _ := strconv.Atoi(offset)
	limitInt, _ := strconv.Atoi(limit)

	users, totalNumberOfUsers := uh.db.Paginate(offsetInt, limitInt)


	c.JSON(http.StatusOK, gin.H{
		"msg" : "Success",
		"status" : "200",
		"totalNumberOfUsers": totalNumberOfUsers,
		"offset" : offsetInt,
		"limit" : limitInt,
		"data" : users,
	})
}

func (uh *UserHandler) GetUsersByEmailFilter (c *gin.Context) {
	keyword := c.DefaultQuery("email", "")

	if keyword == "" {
		c.JSON(http.StatusBadRequest, gin.H{"error": "Please enter a keyword to filter for"})
        return
	}

	res, count := uh.db.FilterByEmail(keyword)

	c.JSON(http.StatusOK, gin.H{
		"msg": "Success",
		"emails": res,
		"numberOfEmails": count,
	})

}

func (uh *UserHandler) GetAllUsers(c *gin.Context) {
	res := uh.db.GetAllUsers()

	c.JSON(http.StatusOK, gin.H{
		"msg" : "Success",
		"users" : res,
	})
}