call clean
"C:\Program Files (x86)\MiKTeX 2.9\miktex\bin\miktex-pdftex.exe" -undump=pdflatex main.tex
"C:\Program Files (x86)\MiKTeX 2.9\miktex\bin\biber.exe" main
"C:\Program Files (x86)\MiKTeX 2.9\miktex\bin\miktex-pdftex.exe" -undump=pdflatex main.tex