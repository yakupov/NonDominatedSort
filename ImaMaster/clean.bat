for %%f in (main.*) do (
	if not [%%f] == [main.bib] if not [%%f] == [main.tex] del %%f
)