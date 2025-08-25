package server

import (
	"github.com/KentoBaguetti/wello-tech-assignment/db"
	"github.com/KentoBaguetti/wello-tech-assignment/handlers"
	"github.com/gin-gonic/gin"
)

func NewServer() *gin.Engine {

	router := gin.Default()

	// int database
	userDatabase := db.CreateDB()

	// init the db handlers
	userHandler := handlers.NewUserHandler(&userDatabase)

	api := router.Group("/api") 
	{
		api.POST("/user", userHandler.CreateUser) // creste one user
		api.GET("/users", userHandler.Paginate) // return a page of users from the db
		api.GET("/users/filter", userHandler.GetUsersByEmailFilter) // filter db based on email
		api.GET("/allusers", userHandler.GetAllUsers) // return all users
	}

	return router

}