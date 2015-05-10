pwd
cd pic
pwd
for %%a in (1 2 3 4 5 6 8 9 10 mpx log) do (
    if exist *.%%a del *.%%a
)
cd ..
pwd

for %%f in (main.*) do (
	if not [%%f] == [main.bib] if not [%%f] == [main.tex] del %%f
)