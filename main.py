# Mark Myers
# WGU C964 Capstone Project

import pandas as pd
import matplotlib.pyplot as plt
import re
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.metrics.pairwise import cosine_similarity
from collections import defaultdict

movies = pd.read_csv('movies.csv', encoding='latin-1')

# The columns used in the machine learning recommendation function are combined and made into a single column.
cols = ['name', 'rating', 'score', 'genre', 'director', 'writer', 'star']
movies['data'] = movies[cols].apply(lambda row: ' '.join(row.values.astype(str)), axis=1)

# The initial process of the machine learning is the CountVectorizer and the cosine similarity.  It takes the data
# created and creates vectors of the data.
cv = CountVectorizer(max_features=2500, stop_words='english')
vectors = cv.fit_transform(movies['data']).toarray()
similarity = cosine_similarity(vectors)


# A descriptive method displaying the average score of movies based on genre.  The genres and average scores are loaded
# into separate lists.  The information is loaded into a pie chart with the wedges representing the movie genre, and
# the wedge width representing the average score.  The averages are also displayed on the appropriate wedge.
def genreAverageScores():
    genreList = []
    scoreList = []
    for i in movies.iterrows():
        if i[1][2] not in genreList:
            genreList.append(i[1][2])
    genreList.sort()

    k = movies[['genre', 'score']].groupby(['genre']).mean()
    scoreList.extend(k['score'].tolist())

    fig1, chart = plt.subplots()
    plt.title('Average Movie Score by Genre')
    chart.pie(scoreList, labels=genreList, autopct='%.1f', shadow=True)
    chart.axis('equal')
    plt.show()


# A descriptive method displaying the quantity of movies based on their rating.  The ratings are loaded into a
# dictionary and then separated by keys and values with the key representing the ratings and the values representing
# the quantity.  The information is loaded into a horizontal bar graph and displayed to the user.
def movieRatingQuantity():
    ratingDict = defaultdict(int)

    for i in movies.iterrows():
        ratingDict[i[1][1]] += 1

    ratingList = list(ratingDict.keys())
    numRatingList = list(ratingDict.values())
    plt.figure(figsize=(9, 5))
    plt.barh(ratingList, numRatingList)
    plt.grid(color='grey', linestyle='-.', linewidth=0.5, alpha=0.2)
    plt.xlabel('Num. of Ratings')
    plt.ylabel('Ratings')
    plt.title('Number of Movies by Rating')
    plt.show()


# A descriptive method displaying the average score per rating.  The ratings and average scores are loaded into separate
# lists.  That information is loaded into a scatter plot with the ratings representing the X axis, and the average
# scores representing the Y axis.
def ratingAverageScores():
    ratingList = []
    scoreList = []
    for i in movies.iterrows():
        if i[1][1] not in ratingList:
            ratingList.append(i[1][1])
    ratingList.sort()

    k = movies[['rating', 'score']].groupby(['rating']).mean()
    scoreList.extend(k['score'].tolist())

    plt.scatter(ratingList, scoreList)
    plt.title('Average Score Per Rating')
    plt.xlabel('Rating')
    plt.ylabel('Average Score')
    plt.show()


# A method to supply a recommendation to the user based on searching for a genre, director, or leading actor.  The
# information is displayed with the highest scored movie at the top of the list.
def getRecommendation(searchName, searchType):
    print(movies[['name', 'rating', 'score', 'star', 'director', 'runtime']].loc[
              (movies['score'] >= 7.5) & (movies[searchType].str.contains(searchName, na=False, flags=re.I))]
          .sort_values('score', ascending=False).to_string(index=False))


# A non-descriptive method using machine learning to make a recommendation to the user.  The user inputs a movie title,
# and the title is searched for in the list.  The CountVectorizer creating the numerical vector for the similarities,
# and they are transformed into an array of vectors using cosine similarity.  The vectors are sorted according to the
# entered title, and the top ten recommendations are displayed to the user.
def getRecommendationByTitle(title):
    movieList = movies[movies['name'].str.contains(title, flags=re.I)]

    if len(movieList):
        index = movieList.index[0]
        movieList = sorted(list(enumerate(similarity[index])), reverse=True, key=lambda x: x[1])[1:11]

        print()
        print("Here are your recommendations based on '{0}': \n".format(title))
        for i in movieList:
            print(movies.name.iloc[i[0]])
    else:
        print('Movie not found.  Please try again.')
        return
    print()


# The user interface is broken down into search, view, and get recommendation options.  More options are contained
# within each of these categories.  Within each category, there are check to ensure that the input is correct or else
# a message will be displayed about the incorrect input.
primaryInput = ''
secondaryInput = ''
while primaryInput != '0':
    print('-----Welcome to Movie Buddy!-----')
    print()
    print('1:  Search')
    print('2:  View')
    print('3:  Get recommendation')
    print('0:  Exit')
    primaryInput = input('Please make a selection: ')
    print()

    if primaryInput == '0':
        break
    elif (primaryInput == '1') or (primaryInput == '2') or (primaryInput == '3'):
        while secondaryInput != '0':
            if secondaryInput == '0':
                print('Thank you for using Movie Buddy!')
                exit()
            if int(primaryInput) == 1:
                print('---Search---')
                print('1:  Movie title')
                print('2:  Lead actor')
                print('3:  Director')
                print('4:  Main menu')
                print('0:  Exit')
                secondaryInput = input('Please make a selection: ')
                print()

                if secondaryInput == '1' or secondaryInput == '2' or secondaryInput == '3' or secondaryInput == '4':
                    if int(secondaryInput) == 1:
                        userInput = input('Title: ')

                        print(movies[['name', 'rating', 'score', 'star', 'director', 'runtime']].loc[
                                  movies['name'].str.contains(userInput, na=False, flags=re.I)].to_string(index=False))
                    elif int(secondaryInput) == 2:
                        userInput = input('Lead actor: ')

                        print(movies[['name', 'rating', 'score', 'star', 'director', 'runtime']].loc[
                                  movies['star'].str.contains(userInput, na=False, flags=re.I)].to_string(index=False))
                    elif int(secondaryInput) == 3:
                        userInput = input('Director: ')

                        print(movies[['name', 'rating', 'score', 'star', 'director', 'runtime']].loc[
                                  movies['director'].str.contains(userInput, na=False, flags=re.I)].to_string(
                            index=False))
                    elif int(secondaryInput) == 4:
                        break
                elif int(secondaryInput) == 0:
                    print('Thank you for using Movie Buddy!')
                    exit()
                else:
                    print('Invalid input.  Please try again.')
                    print()
            elif int(primaryInput) == 2:
                print('---View---')
                print('1:  Highest scored movies')
                print('2:  Lowest scored movies')
                print('3:  Average score by genre')
                print('4:  Number of movies by rating')
                print('5:  Average score by rating')
                print('6.  Main menu')
                print('0:  Exit')
                secondaryInput = input('Please make a selection:  ')
                print()
                if secondaryInput == '1' or secondaryInput == '2' or secondaryInput == '3' or secondaryInput == '4' or \
                        secondaryInput == '5' or secondaryInput == '6':
                    if int(secondaryInput) == 1:
                        print(movies[['name', 'rating', 'score', 'star', 'director', 'runtime']].loc[
                                  movies['score'] >= 8.5].sort_values('score', ascending=False).to_string(index=False))
                        print()
                    elif int(secondaryInput) == 2:
                        print(movies[['name', 'rating', 'score', 'star', 'director', 'runtime']].loc[
                                  movies['score'] <= 3].sort_values('score', ).to_string(index=False))
                        print()
                    elif int(secondaryInput) == 3:
                        genreAverageScores()
                    elif int(secondaryInput) == 4:
                        movieRatingQuantity()
                    elif int(secondaryInput) == 5:
                        ratingAverageScores()
                    elif int(secondaryInput) == 6:
                        break
                elif secondaryInput == '0':
                    print('Thank you for using Movie Buddy!')
                    exit()
                else:
                    print('Invalid input.  Please try again.')
                    print()
            elif int(primaryInput) == 3:
                print('---Get Recommendation---')
                print('1.  By title')
                print('2.  By genre')
                print('3.  By lead actor')
                print('4.  By director')
                print('5.  Main menu')
                print('0.  Exit')
                secondaryInput = input('Please make a selection: ')
                print()
                if secondaryInput == '1' or secondaryInput == '2' or secondaryInput == '3' or secondaryInput == '4' or \
                        secondaryInput == '5' or secondaryInput == '6':
                    if int(secondaryInput) == 1:
                        userInput = input('Please enter a title: ')
                        getRecommendationByTitle(userInput)
                    elif int(secondaryInput) == 2:
                        print(
                            'Genres: Action, Adventure, Animation, Biography, Comedy, Crime, Drama, Family, Fantasy')
                        print('\t\t\t\t\tHistory, Horror, Music, Musical, Mystery, Romance, Sport, Thriller, Western')
                        userInput = input('Please enter a genre: ')
                        getRecommendation(userInput, 'genre')
                    elif int(secondaryInput) == 3:
                        userInput = input('Please enter a lead actor: ')
                        getRecommendation(userInput, 'star')
                    elif int(secondaryInput) == 4:
                        userInput = input('Please enter a director: ')
                        getRecommendation(userInput, 'director')
                    elif int(secondaryInput) == 5:
                        break
                elif secondaryInput == '0':
                    print('Thank you for using Movie Buddy!')
                    exit()
                else:
                    print('Invalid input.  Please try again.')
                    print()
    else:
        print('Invalid input.  Please try again.')
        print()
print('Thank you for using Movie Buddy!')
