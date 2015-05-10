call clean

cd pic
for %%i in (*.mp) do (
    "C:\Program Files (x86)\MiKTeX 2.9\miktex\bin\mpost" -tex=latex %%i
)
cd ..

"C:\Program Files (x86)\MiKTeX 2.9\miktex\bin\miktex-pdftex.exe" -undump=pdflatex main.tex
"C:\Program Files (x86)\MiKTeX 2.9\miktex\bin\biber.exe" main
"C:\Program Files (x86)\MiKTeX 2.9\miktex\bin\miktex-pdftex.exe" -undump=pdflatex main.tex