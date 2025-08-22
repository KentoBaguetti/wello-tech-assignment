package db

import (
	"strings"

	"github.com/KentoBaguetti/wello-tech-assignment/models"
)

type UserDB struct {
	users           map[int][]*models.User
	pageCount       int
	maxUsersPerPage int
}

func CreateDB() UserDB {
	firstPage := make([]*models.User, 0)
	tmpDb := UserDB{users: make(map[int][]*models.User), pageCount: 1, maxUsersPerPage: 10}
	tmpDb.users[1] = firstPage
	return tmpDb
}

func (db *UserDB) AddUser(user *models.User) {

	if len(db.users[db.pageCount]) >= db.maxUsersPerPage {
		db.pageCount++
		newPage := make([]*models.User, 0)
		db.users[db.pageCount] = newPage
	}

	currPage := db.users[db.pageCount]

	updatedPage := append(currPage, user)

	db.users[db.pageCount] = updatedPage

}

func (db *UserDB) FilterByEmail(keyword string) []string {

	res := make([]string, 0)

	for i := 1; i <= db.pageCount; i++ {
		currPageIndex := i

		for _, user := range db.users[currPageIndex] {
			if strings.Contains(user.Email, keyword) {
				res = append(res, user.Email)
			}
		}

	}

	return res

}

func (db *UserDB) GetPage(index int) []string {
	res := make([]string, 0)

	for _, user := range db.users[index] {
		res = append(res, user.Email)
	}

	return res

}

func (db *UserDB) GetAllUsers() map[int][]*models.User {
	return db.users
}

func (db *UserDB) GetPageCount() int {
	return db.pageCount
}

func (db *UserDB) CheckIfEmailExists(email string) bool {

	for i := 1; i <= db.pageCount; i++ {
		for _, user := range db.users[i] {
			if user.Email == email {
				return true
			}
		}
	}

	return false
}