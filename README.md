# Jeopardy

*Contestant*: "Java Applications for 600 please."  
*Host*: "A fun trivia application that you can enjoy with your friends."  
*Contestant*: \*\***Buzz**\*\* "What is Jeopardy?"  
*Host*: "Correct!" 

Whether you are a trivia enthusiast or just a casual player, this Jeopardy application will get your competitive spirits 
going while also testing your knowledge on a variety of interesting topics. Compete against a group of friends 
(or enemies) to see who is the trivia master! What are you waiting for? 

## Features
- **The more the merrier**: Create a jeopardy game with 2-4 players. Set your own alias before the game starts.
- **Simple mechanics**: Choose questions from the game board by clicking on them. Buzz in to answer questions by 
pressing a key on the keyboard. Answer questions by typing your answer into a submission box.
- **Show off your accomplishments**: Compare stats with other players at the end of the game with the help of a 
scoreboard containing the earnings of each player, the player that answered the most questions, and a variety of other 
interesting information.
- **Never get the same board twice**: Randomized categories and questions ensure that each game you play is unique.
- **One more round?**: Decide if you want to play another round, add new players, or end the game after a round is over. 

## Inspiration
As a child, I remember watching Jeopardy on T.V. with my siblings and failing miserably to answer any of the questions.
Although this was the case, I still enjoyed the competitiveness and overall atmosphere the show brought to my family
whenever we were watching it. As such, one of my main motives for creating this project was to restore some of this 
nostalgia from my younger years. The interactiveness of the application compared to just watching the show on television
would reignite some of those old feelings, while also generating a more competitive environment. In addition, this 
project was a great opportunity to apply the knowledge I learned in class to develop something that I am interested in 
and have a passion to work on. In particular, the challenge of creating a multiplayer application enticed me, as I did 
not have experience in this field. The knowledge I gained from working on this project will be a valuable asset as I 
move in my academic and professional career.

## User Stories
- I want to be able to add questions to a category
- I want to be able to choose a question from a category
- I want to be able to answer a question 
- I want to be able to see my total earnings after each question, and my total earnings after the game ends
- I want to have the option to save my game before quitting
- I want to have the option to load my saved game instead of creating a new game

## Phase 4: Task 2
*Option*: Make appropriate use of the `Map` interface somewhere in your code
- Class involved: `Contestant`
- Methods involved: `updateCategoriesPicked(categoryName : String)`, `favoriteCategory()` 

## Phase 4: Task 3
- Many of the "Panel" classes have similar behavior (i.e. initializing graphics, initializing fields) and are associated 
with the same things -> I could create an abstract class called "Panel" to contain the commonalities between the classes
- The "Frame" classes have similar behavior (i.e. initializing the graphics for the frame) -> I could create an abstract 
class called "Frame" to contain the commonalities between the classes
- Reduce coupling between classes by introducing getter methods 
    - Example: Reader, MenuPanel, and NewGamePanel -> NewGamePanel is already associated with MenuPanel, so I could add 
    a getter method to MenuPanel to get the Reader from it instead of having MenuPanel have its own association with
    Reader
    - Example: Game, GamePanel, and GameFrame -> GamePanel is already associated with GameFrame, so I could add a getter
    method to GameFrame to get the Game from it instead of having GamePanel have its own association with GameFrame

 
