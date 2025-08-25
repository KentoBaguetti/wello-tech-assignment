package db

import (
	"strings"

	"github.com/KentoBaguetti/wello-tech-assignment/models"
)

type UserDB struct {
	users         []models.User
	numberOfUsers int
	knownEmails map[string]bool
}

// build an in-memory db with a map. Each key is an integer representing a page number, with the values representing vectors
// with a max size limit of 10 per page
func CreateDB() UserDB {
	tmpDb := UserDB{users: make([]models.User, 0), numberOfUsers: 0, knownEmails: make(map[string]bool)}
	return tmpDb
}

func (db *UserDB) AddUser(user models.User) {

	// if the page has 10 users, create a new page and start adding to thats
	db.users = append(db.users, user)
	db.knownEmails[user.Email] = true
	db.numberOfUsers++

}

// iterate over all users and add users that match the email filter
func (db *UserDB) FilterByEmail(email string) ([]models.User, int) {

	count := 0
	res := make([]models.User, 0)

	for _, user := range db.users {
		if strings.Contains(user.Email, email) {
			res = append(res, user)
			count ++
		}
	}

	return res, count

}

// return a page of users from the db using the offset pagiantion design
func (db *UserDB) Paginate(offset, limit int) ([]models.User, int) {
	
	if offset <= 0 {
		offset = 0
	}

	totalNumberOfUsers := db.GetNumberOfUsers()

	if limit <= 0 {
		return make([]models.User, 0), totalNumberOfUsers
	}

	if offset > totalNumberOfUsers {
		return make([]models.User, 0), totalNumberOfUsers
	}

	endIndex := offset + limit

	if endIndex > totalNumberOfUsers {
		endIndex = totalNumberOfUsers
	}

	return db.users[offset:endIndex], totalNumberOfUsers

}

func (db *UserDB) GetAllUsers() []models.User {
	return db.users
}

func (db *UserDB) GetNumberOfUsers() int {
	return db.numberOfUsers
}

