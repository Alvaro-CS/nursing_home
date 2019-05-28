~~Instructions~~ 
First of all, when you run the app, it will ask you what entity do you want to manage from the database.
We have 7 different menus in our app. In 6 of them (worker, residents, rooms, activities, drugs, treatments) you can create a new entity of that type with its attributes. 
You can also show a list of each different entity showing their id and name. 


There’s also an option in the menu  so you can see the details of each one. If it’s a worker or a resident you can see also their photo. 
In the case of a room you can also see the list of residents that it has. In activities, it is shown all the residents that have signed up to the activity and also the workers
 in charge of it. Drug doesn’t have a details menu because it doesn’t have any. Treatment is a very special entity because it’s related to many drugs and it’s dosage(many to many
 relationship with an attribute), so in the details we show all the attributes of the treatment including each drug that it has with it’s dosage.


It’s implemented a special option in drug, in which is searched by name column instead of id. This will check if a drug is in the database by its name and if not, the system will
ask you if you want to add it.


There’s and option to delete and update each entity. Update isn’t available in drug because it has only one attribute, so there’s only the option to delete it. Update treatment is
 particular. You can update the attributes and also the dosage of the drug you want.


Inside the majority of the menus, we have extra menus to manage the different many to many relationships. In worker and resident there’s an option to add some workers to the daily
 care of a resident (and also to delete the connection if needed). In activities, there are two different menus: one to sign up residents to the activities that are available and
 other to assign workers to be in charge to the different activities (and also to drop-out the activity).


Finally, we find the last option of the app that allows to manage XML files. You can transform all room and activity objects of the whole database to XML files by the option “Marshall”.
 The room xml file also includes its residents. This creates the opportunity to use the option “HTML conversion”. The XML files created by “Marshall” will be translated to create a 
webpage with all the rooms with their residents and all activities that were in the database. With “Unmarshall” you can use a previously marshalled room and activity xml file if you 
want to add those residents with their rooms and activities to the database. 


There can be found a “Webpage.html” file in “xmls” folder. That’s a prototype version for making easier the daily lives of the workers in the nursing home: they can see all the 
information of the patients and their rooms, with the activities available.

~~~~~~NURSING HOME DATABASE SYSTEM 2019
Copyright 2019 Álvaro Cascajero, Susana del Riego, Clara Ulloa, Laura Blanco