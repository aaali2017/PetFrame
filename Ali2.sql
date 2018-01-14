query 1
select pname, spname, fname, lname
	from pet natural join species, owner
	order by pname;
pause;
query 2
select pname, breed
	from pet
	where breed = 'Retriever';
pause;
query 3
select avg(weight)
	from pet;
pause;
query 4
select pname
	from pet
	where weight >= 60 and weight <= 70;
pause;
query 5
select pname, breed, fname, lname
	from pet natural join species, owner
	group by breed;
pause;
query 6
select pname, fname, lname
	from pet natural join owner
	group by fname, lname;
pause;
query 7
select breed, count (breed)
	from pet
	having count(breed) > 1;
pause;
query 8
select spname, avg(weight)
	from pet natural join species;