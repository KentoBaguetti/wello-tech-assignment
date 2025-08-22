package main

import (
	"fmt"

	"github.com/KentoBaguetti/wello-tech-assignment/server"
	"github.com/gin-gonic/gin"
)

func main() {

	fmt.Println("Running main")

	router := server.NewServer()

	router.GET("/", func(ctx *gin.Context) {
		ctx.JSON(200, gin.H{
			"msg" : "Hello. Gin server running on 8081",
		})
	})

	if err := router.Run(":8081"); err != nil {
		fmt.Printf("Error startign server: %s", err)
	}

}