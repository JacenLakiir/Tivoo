========================================
README: Tivoo
========================================

Names:
- Siyang Chen (sc146)
- Hui Dong (hd37)
- Ian McMahon (icm9)
- Eric Mercer (ewm10)


========================================
Prototype
========================================

Started:  Feb 12
Finished: Feb 13

Project Length:
- [Actual] 2 hours at group meeting + 4 hours Eric + 2 hours Siyang + 4 hours Hui + 1 hour Ian = 13
- [Estimated] 10 hours
- [How good our estimate was] While our estimate wasn't too far off, learning the specifics of Gagawa, SAX, and Java Calendar contributed most of the time.
		The programming itself for a prototype/first draft was fairly straight forward. 

Discussed with:
- UTA Mason Meier (assigned UTA)

Resources:
- Gagawa library (http://code.google.com/p/gagawa/)

Files Used to Start and Test:
- none

Data / Resource Files Required:
- various XML files (included in the data folder)
- gagawa-1.0.1 JAR (included in the lib folder)

Impressions:

Input portion:
Messing with Calendar is somewhat annoying. Also, our current code style is not the best, but this is only the prototype. Other than that, it wasn't especially hard.
	- Siyang
I have worked on the duke calendar parser, the parser part was not too hard. But I've got troubles in using java Calendar. Siyang was albe to fix the bugs in using the Calendar.
	- Hui

Filtering:
The filtering was very straight forward as we encapsulated each input feed into identical objects that could be easily sorted through.
	-Ian

Output portion:
Creating the appropriate directories and files and generating HTML for the output stage took some experimentation, especially in terms of using the Gagawa library, but overall prototyping the output stage was a fairly straightforward process.
	-Eric



========================================
Part 1
========================================

Started:  Feb 14
Finished: Feb 15

Project Length:
- [Actual] 3 hours Eric + 2.5 hours Hui + 7 hours Siyang = 12.5 hours total
- [Estimated] 10 hours
- [How good our estimate was] Not bad.

Discussed with:
- UTA Mason Meier (assigned UTA)

Resources:
- Gagawa library (http://code.google.com/p/gagawa/)

Files Used to Start and Test:
- none

Data / Resource Files Required:
- various XML files (included in the data folder)
- gagawa-1.0.1 JAR (included in the lib folder)

Impressions:

Input portion:
SAX parser conventions are somewhat annoying. Also, I'm not entirely satisfied with the way typechecking/parsing design, but it works.
	- Siyang
The main problems with parsing the xmltv files are: 1. There is a DOCTYPE in xmltv file and a way should be found to ignore it, otherwise there will be a filenotfound exception; 2. The channel names are stored separately from programmes, the programmes only has the id for the channel name.
	- Hui

Filtering:
	-Ian

Output portion:
Allowing for events to be shown on the summary page sorted by weekday took a fair amount of work due to the need to familiarize myself with the Calendar API. Fixing the formatting of event times to be consistent with normal clock displays (i.e. "12:00 PM" rather than "0:0") also took a bit of time since I needed to read through the Formatter API, which greatly reduced the amount of code I needed to write to accomplish this.
	-Eric




========================================
Part 2
========================================

Started:  Feb 23
Finished: Feb 26

Project Length:
- [Actual] 2 hours (Siyang) + 1 hour (Ian) + 4 hours (Hui) + 4 hours (Eric) = 11 hours total
- [Estimated] 10 hrs
- [How good our estimate was] Slightly underestimated the actual length

Discussed with:
- UTA Mason Meier (assigned UTA)

Resources:
- Gagawa library (http://code.google.com/p/gagawa/)

Files Used to Start and Test:
- none

Data / Resource Files Required:
- various XML files (included in the data folder)
- gagawa-1.0.1 JAR (included in the lib folder)

Impressions:

Input portion:
Adding the input formats were pretty straightforward, with the framework we set up. The only issue was with Google calendar.
	- Siyang
The recurring event in Google calendar was annoying, since we cannot obtain the information on how often the event recur or the exact date of the event. 
	- Hui

Filtering:
Filtering was almost identical to part 1, and sorting was fairly simple, as both Calendars and strings have a compareTo method. The only issue was dealing with reversed.
	-Ian

Output portion:
Adding sorted view was incredibly straightforward while implementing the algorithm for finding conflicting events took a fair amount of work. Checking the type of content to be created and keeping track of list tags for the calendar view were tedious.
	-Eric
