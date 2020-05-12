# BibDictionnary
Alternative tool (to the existing ones), called BibCreator. The main task of this tool is to read and process some given .bib files, based on a given author name, and creates 3 formatted files (IEEE, ACM and NJ) with all records of that author(s). 

The tool will require the user to enter an author name, then searches all .bib files for any articles
for any author(s) with that name, and creates 3 different files with the correct reference formats for IEEE,
ACM and NJ with all found records for that author name. The exact names of these files must be called:
author-name-IEEE.json, author-name-ACM.json, and author-name-NJ.json, where author-name is the
exact name of the author being searched. You should notice that the search is based on one single name
(i.e. family name), and the tool will create the files to include all the records of any/all author(s) with that
name. That is, the created files may include records of different authors who happen to have the same
name. For instance, records for J. Park, and T. L. Park must both be included if the search was based on
an author with the name Park. The resulted files in such case will consequently be called Park-IEEE.json,
Park-ACM.json, and Park-NJ.json. As a side note, to distinguish our tool/application from other existing
similar software, we will call these files author-name-IEEE.json, author-name-ACM.json, and authorname-NJ.json (although in fact, the created files contain the references to the articles, and not json
records!).
