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

type Email struct {
	Email string `json:"email" binding:"required"`
}

func NewUserHandler(database *db.UserDB)  *UserHandler {
	temp := UserHandler{db: database}
	return &temp
}

func (uh *UserHandler) CreateUser(c *gin.Context) {
	var emailData Email

	if err := c.ShouldBindJSON(&emailData); err != nil {
		 c.JSON(400, gin.H{"msg": "Error creating user"})
        return
	}

	if emailData.Email == "" {
		c.JSON(400, gin.H{"msg": "Please enter an email"})
        return
	}

	if !strings.Contains(emailData.Email, "@") {
		c.JSON(400, gin.H{"msg": "Please enter a valid email address"})
        return
	}

	if uh.db.CheckIfEmailExists(emailData.Email) {
		c.JSON(400, gin.H{"msg": "Email already exists"})
        return
	}

	pNewUser := models.User{Email: emailData.Email}

	uh.db.AddUser(&pNewUser)

	c.JSON(http.StatusOK, gin.H{
		"msg" : "Successfully added user",
		"PageNumberAddedTo" : uh.db.GetPageCount(),
		"email" : emailData.Email,
	})

}

func (uh *UserHandler) GetUserByPageNumber(c *gin.Context) {
	idStr := c.Param("id")

	if idStr == "" {
		c.JSON(http.StatusBadRequest, gin.H{"error": "Please enter a page number"})
        return
	}

	id, err := strconv.Atoi(idStr)

	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error message" : "Please enter an integer page number"})
		return
	}

	if id < 1 || id > uh.db.GetPageCount() {
		c.JSON(http.StatusBadRequest, gin.H{
			"error message" : "Please enter an integer page number",
			"maxPages" : uh.db.GetPageCount(),
		})
		return
	}

	res := uh.db.GetPage(int(id))

	c.JSON(http.StatusOK, gin.H{
		"msg" : "Success",
		"pageNo" : id,
		"totalNumberOfPages": uh.db.GetPageCount(),
		"emails" : res,
	})
}

func (uh *UserHandler) GetUsersByEmailFilter (c *gin.Context) {
	keyword := c.Param("keyword")

	if keyword == "" {
		c.JSON(http.StatusBadRequest, gin.H{"error": "Please enter a keyword to filter for"})
        return
	}

	res := uh.db.FilterByEmail(keyword)

	c.JSON(http.StatusOK, gin.H{
		"msg": "Success",
		"emails": res,
	})

}

func (uh *UserHandler) GetAllUsers(c *gin.Context) {
	res := uh.db.GetAllUsers()

	c.JSON(http.StatusOK, gin.H{
		"msg" : "Success",
		"users" : res,
	})
}