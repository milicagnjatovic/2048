# 2048
Replica of the game 2048 which already exists on Google play store.

## Instructions
You can use <kbd>&#8592;</kbd>, <kbd>&#8593;</kbd>, <kbd>&#8594;</kbd> and <kbd>&#8595;</kbd> key to play the game. By pressing it
 you move all the fields containing numbers to one side. While moving two fields with same numbers they collide in one field.
 
_For exmaple:_

 * | 2 | 2 | will become | 4 | 0 | while moving left 
 * If you have | 2 | 2 | 2 | 2 | you wont get 8, but you'll get two 4s, | 4 | 4 | 0 | 0 |, and now you can get 8 with next move

Every time you make a move new field appears on the board.

Goal is to get highest possible number, game is called 2048 because it's pretty good gaol, but you can make even higher scores.
You lose if after move there's no space for new field.

You can chose what size of board you want for the game. Smaller the board is it's harder to play.
 
Good luck :)

## Program summary
Game made in Java using JavaFX.
