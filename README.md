# Local-Data-Repository-RAF-SK

University project for the course Software Components.
Acts as a local database that stores a variety of entities in a specific format (JSON, YAML and a custom format).

## TODO:
- [X] Create a specification component
- [X] Making an Entity class
  - [X] Auto generate ID by choice
- [X] Create JSON implementation
- [X] Create YAML implementation
- [X] Create custom format implementation
- [ ] CRUD operations:
  - [X] Add
  - [ ] Update
  - [X] Delete
- [ ] Filter data
  - [X] Filter data by given entity name
  - [ ] Filter data by given attributes and their values
- [ ] Sort data
- [ ] GUI test application

## Custom format example
```
<
	(
		"id" -> 1;
		"name" -> "Student";
		"attributes" -> {
			"ime" -> "Pera",
			"prezime" -> "Peric",
			"smer" -> "RN",
			"godina upisa" -> 2018
		}
	),
	(
		"id" -> 2;
		"name" -> "Lokacija";
		"attributes" -> {
			"grad" -> "Beograd",
			"adresa" -> "Knez Mihailova 6",
			"postanski broj" -> 11000
		}
	),
	(
		"id" -> 3;
		"name" -> "Student";
		"attributes" -> {
			"ime" -> "Pera",
			"prezime" -> "Peric",
			"fakultet" -> (
				"id" -> 4;
				"name" -> "Fakultet";
				"attributes" -> {
					"ime fakulteta" -> "Racunarski Fakultet",
					"smer" -> "RI",
					"godina upisa" -> 2016
				}
		}
	)
>
```

## Authors
Student | Broj indeksa | E-mail
--------|--------------|-------
Jovan Babic | RN 30/2018 | jbabic3018rn@raf.rs
Filip Cmiljanovic | RN 5/2018 | fcmiljanovic518rn@raf.rs
