# Minesweeper
A classic computer game. Popularized by Microsoft and loved by me since 2016.  
Written as my first android app. At the beginning it was just a uni project.  
After some time I upgraded it with some new features.  
The app uses my REST API: [Global Ranking](https://github.com/slawomirbuczek/Minesweeper-ranking).

## Current features:
* game:
  * default minesweeper's mechanics
  * three levels:
    * easy - 7 columns, 0.1 mines to fields ration
    * medium - 8 columns, 0.15 mines to fields ration
    * hard - 9 columns, 0.2 mines to fields ration
  * local ranking
* client:
  * registration & login
  * each game won is automatically posted to api
  * global ranking - top 50 records by each level
  * player statistics

## Galery
<img src="https://user-images.githubusercontent.com/75256359/111480945-c6e5de00-8732-11eb-847b-80c016c4de43.jpg" alt="login" width="200"/> <img src="https://user-images.githubusercontent.com/75256359/111480768-9dc54d80-8732-11eb-84b7-339717a8fd04.jpg" alt="menu" width="200"/> <img src="https://user-images.githubusercontent.com/75256359/111480844-b170b400-8732-11eb-9002-2f92ddc60a6d.jpg" alt="inprogress" width="200"/> <img src="https://user-images.githubusercontent.com/75256359/111480888-bc2b4900-8732-11eb-81a4-debd49cc9d1a.jpg" alt="win" width="200"/> <img src="https://user-images.githubusercontent.com/75256359/111481681-7de25980-8733-11eb-84a0-2ec5cd38df9b.jpg" alt="lost" width="200"/> <img src="https://user-images.githubusercontent.com/75256359/111480989-d1a07300-8732-11eb-83fc-28c8a1a01f11.jpg" alt="local" width="200"/> <img src="https://user-images.githubusercontent.com/75256359/111481022-d9f8ae00-8732-11eb-82ba-d48ae2e56676.jpg" alt="global" width="200"/> <img src="https://user-images.githubusercontent.com/75256359/111481945-bc781400-8733-11eb-80ff-2d98823a23ca.jpg" alt="stats" width="200"/>


## Technology stack and its application in the project:
  * Android app created with Java
  * Local ranking created with SQLite
  * REST template from Spring for Android
