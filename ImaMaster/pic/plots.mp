verbatimtex
%&latex
\documentclass{article}
\begin{document}
etex

pickup pencircle scaled 0.2pt;

input graph;
input sarith;

vardef colPoint(expr c) =
    picture p; p := nullpicture;
    addto p contour fullcircle scaled 5pt withcolor c;
    p
enddef;

def gridding = 
    autogrid(, grid.lft) withcolor .85white;
        
    for i=250,500,1000,2000,4000:
        grid.bot("%g", i) withcolor .85white;
    endfor;
enddef;

beginfig(1)
    draw begingraph(3.7in, 1.5in);
        setcoords(log, log);
        glabel.bot(btex $N$ etex, OUT);
        glabel.lft(btex Comparisons etex rotated 90, OUT);
       
        gdraw "dat/square-fastNDS-comp.dat"     withcolor red;
        gdraw "dat/square-ENLU-comp.dat"        withcolor green;
        gdraw "dat/square-proposed-comp.dat"    withcolor blue;

        gridding;
        
        gdraw "dat/square-fastNDS-comp.dat"     withcolor red;
        gdraw "dat/square-ENLU-comp.dat"        withcolor green;
        gdraw "dat/square-proposed-comp.dat"    withcolor blue;

        gdraw "dat/square-fastNDS-comp.dat"     plot colPoint(red);
        glabel.ulft(btex Fast NDS etex, 2);
        gdraw "dat/square-ENLU-comp.dat"        plot colPoint(green);
        glabel.lrt(btex ENLU etex, 3);
        gdraw "dat/square-proposed-comp.dat"    plot colPoint(blue);
        glabel.lrt(btex Proposed etex, 3);
    endgraph;
endfig;

beginfig(2)
    draw begingraph(3.7in, 1.5in);
        setcoords(log, log);
        glabel.bot(btex $N$ etex, OUT);
        glabel.lft(btex Time, s etex rotated 90, OUT);
        
        gdraw "dat/square-fastNDS-time.dat"     withcolor red;
        gdraw "dat/square-ENLU-time.dat"        withcolor green;
        gdraw "dat/square-proposed-time.dat"    withcolor blue;

        gridding;
        
        gdraw "dat/square-fastNDS-time.dat"     withcolor red;
        gdraw "dat/square-ENLU-time.dat"        withcolor green;
        gdraw "dat/square-proposed-time.dat"    withcolor blue;

        gdraw "dat/square-fastNDS-time.dat"     plot colPoint(red);
        glabel.ulft(btex Fast NDS etex, 2);
        gdraw "dat/square-ENLU-time.dat"        plot colPoint(green);
        glabel.lrt(btex ENLU etex, 3);
        gdraw "dat/square-proposed-time.dat"    plot colPoint(blue);
        glabel.lrt(btex Proposed etex, 3);
    endgraph;
endfig;

beginfig(3)
    draw begingraph(3.7in, 1.5in);
        setcoords(log, log);
        glabel.bot(btex $N$ etex, OUT);
        glabel.lft(btex Comparisons etex rotated 90, OUT);
       
        gdraw "dat/parallel-fastNDS-comp.dat"     withcolor red;
        gdraw "dat/parallel-ENLU-comp.dat"        withcolor green;
        gdraw "dat/parallel-proposed-comp.dat"    withcolor blue;

        gridding;
        
        gdraw "dat/parallel-fastNDS-comp.dat"     withcolor red;
        gdraw "dat/parallel-ENLU-comp.dat"        withcolor green;
        gdraw "dat/parallel-proposed-comp.dat"    withcolor blue;

        gdraw "dat/parallel-fastNDS-comp.dat"     plot colPoint(red);
        glabel.ulft(btex Fast NDS etex, 2);
        gdraw "dat/parallel-ENLU-comp.dat"        plot colPoint(green);
        glabel.lrt(btex ENLU etex, 3);
        gdraw "dat/parallel-proposed-comp.dat"    plot colPoint(blue);
        glabel.lrt(btex Proposed etex, 3);
    endgraph;
endfig;

beginfig(4)
    draw begingraph(3.7in, 1.5in);
        setcoords(log, log);
        glabel.bot(btex $N$ etex, OUT);
        glabel.lft(btex Time, s etex rotated 90, OUT);
        
        gdraw "dat/parallel-fastNDS-time.dat"     withcolor red;
        gdraw "dat/parallel-ENLU-time.dat"        withcolor green;
        gdraw "dat/parallel-proposed-time.dat"    withcolor blue;

        gridding;
        
        gdraw "dat/parallel-fastNDS-time.dat"     withcolor red;
        gdraw "dat/parallel-ENLU-time.dat"        withcolor green;
        gdraw "dat/parallel-proposed-time.dat"    withcolor blue;

        gdraw "dat/parallel-fastNDS-time.dat"     plot colPoint(red);
        glabel.ulft(btex Fast NDS etex, 2);
        gdraw "dat/parallel-ENLU-time.dat"        plot colPoint(green);
        glabel.lrt(btex ENLU etex, 3);
        gdraw "dat/parallel-proposed-time.dat"    plot colPoint(blue);
        glabel.lrt(btex Proposed etex, 3);
    endgraph;
endfig;

beginfig(5)
    draw begingraph(3.7in, 1.5in);
        setcoords(log, log);
        glabel.bot(btex $N$ etex, OUT);
        glabel.lft(btex Comparisons etex rotated 90, OUT);
       
        gdraw "dat/diag1-fastNDS-comp.dat"     withcolor red;
        gdraw "dat/diag1-ENLU-comp.dat"        withcolor green;
        gdraw "dat/diag1-proposed-comp.dat"    withcolor blue;

        gridding;
        
        gdraw "dat/diag1-fastNDS-comp.dat"     withcolor red;
        gdraw "dat/diag1-ENLU-comp.dat"        withcolor green;
        gdraw "dat/diag1-proposed-comp.dat"    withcolor blue;

        gdraw "dat/diag1-fastNDS-comp.dat"     plot colPoint(red);
        glabel.ulft(btex Fast NDS etex, 2);
        gdraw "dat/diag1-ENLU-comp.dat"        plot colPoint(green);
        glabel.lrt(btex ENLU etex, 3);
        gdraw "dat/diag1-proposed-comp.dat"    plot colPoint(blue);
        glabel.lrt(btex Proposed etex, 3);
    endgraph;
endfig;

beginfig(6)
    draw begingraph(3.7in, 1.5in);
        setcoords(log, log);
        glabel.bot(btex $N$ etex, OUT);
        glabel.lft(btex Time, s etex rotated 90, OUT);
        
        gdraw "dat/diag1-fastNDS-time.dat"     withcolor red;
        gdraw "dat/diag1-ENLU-time.dat"        withcolor green;
        gdraw "dat/diag1-proposed-time.dat"    withcolor blue;

        gridding;
        
        gdraw "dat/diag1-fastNDS-time.dat"     withcolor red;
        gdraw "dat/diag1-ENLU-time.dat"        withcolor green;
        gdraw "dat/diag1-proposed-time.dat"    withcolor blue;

        gdraw "dat/diag1-fastNDS-time.dat"     plot colPoint(red);
        glabel.ulft(btex Fast NDS etex, 2);
        gdraw "dat/diag1-ENLU-time.dat"        plot colPoint(green);
        glabel.ulft(btex ENLU etex, 3);
        gdraw "dat/diag1-proposed-time.dat"    plot colPoint(blue);
        glabel.lrt(btex Proposed etex, 3);
    endgraph;
endfig;

beginfig(7)
    draw begingraph(3.7in, 1.5in);
        setcoords(log, log);
        glabel.bot(btex $N$ etex, OUT);
        glabel.lft(btex Comparisons etex rotated 90, OUT);
       
        gdraw "dat/diag2-fastNDS-comp.dat"     withcolor red;
        gdraw "dat/diag2-ENLU-comp.dat"        withcolor green;
        gdraw "dat/diag2-proposed-comp.dat"    withcolor blue;

        gridding;
        
        gdraw "dat/diag2-fastNDS-comp.dat"     withcolor red;
        gdraw "dat/diag2-ENLU-comp.dat"        withcolor green;
        gdraw "dat/diag2-proposed-comp.dat"    withcolor blue;

        gdraw "dat/diag2-fastNDS-comp.dat"     plot colPoint(red);
        glabel.ulft(btex Fast NDS etex, 1);
        gdraw "dat/diag2-ENLU-comp.dat"        plot colPoint(green);
        glabel.ulft(btex ENLU etex, 3);
        gdraw "dat/diag2-proposed-comp.dat"    plot colPoint(blue);
        glabel.lrt(btex Proposed etex, 3);
    endgraph;
endfig;

beginfig(8)
    draw begingraph(3.7in, 1.5in);
        setcoords(log, log);
        glabel.bot(btex $N$ etex, OUT);
        glabel.lft(btex Time, s etex rotated 90, OUT);
        
        gdraw "dat/diag2-fastNDS-time.dat"     withcolor red;
        gdraw "dat/diag2-ENLU-time.dat"        withcolor green;
        gdraw "dat/diag2-proposed-time.dat"    withcolor blue;
        
        gridding;
       
        gdraw "dat/diag2-fastNDS-time.dat"     withcolor red;
        gdraw "dat/diag2-ENLU-time.dat"        withcolor green;
        gdraw "dat/diag2-proposed-time.dat"    withcolor blue;

        gdraw "dat/diag2-fastNDS-time.dat"     plot colPoint(red);
        glabel.lrt(btex Fast NDS etex, 3);
        gdraw "dat/diag2-ENLU-time.dat"        plot colPoint(green);
        glabel.ulft(btex ENLU etex, 1);
        gdraw "dat/diag2-proposed-time.dat"    plot colPoint(blue);
        glabel.ulft(btex Proposed etex, 3);
    endgraph;
endfig;

beginfig(9)
    draw begingraph(3.7in, 1.5in);
        setcoords(log, log);
        glabel.bot(btex $N$ etex, OUT);
        glabel.lft(btex Comparisons etex rotated 90, OUT);
       
        gdraw "dat/parper-fastNDS-comp.dat"     withcolor red;
        gdraw "dat/parper-ENLU-comp.dat"        withcolor green;
        gdraw "dat/parper-proposed-comp.dat"    withcolor blue;

        gridding;
        
        gdraw "dat/parper-fastNDS-comp.dat"     withcolor red;
        gdraw "dat/parper-ENLU-comp.dat"        withcolor green;
        gdraw "dat/parper-proposed-comp.dat"    withcolor blue;

        gdraw "dat/parper-fastNDS-comp.dat"     plot colPoint(red);
        glabel.ulft(btex Fast NDS etex, 3);
        gdraw "dat/parper-ENLU-comp.dat"        plot colPoint(green);
        glabel.ulft(btex ENLU etex, 3);
        gdraw "dat/parper-proposed-comp.dat"    plot colPoint(blue);
        glabel.lrt(btex Proposed etex, 3);
    endgraph;
endfig;

beginfig(10)
    draw begingraph(3.7in, 1.5in);
        setcoords(log, log);
        glabel.bot(btex $N$ etex, OUT);
        glabel.lft(btex Time, s etex rotated 90, OUT);
        
        gdraw "dat/parper-fastNDS-time.dat"     withcolor red;
        gdraw "dat/parper-ENLU-time.dat"        withcolor green;
        gdraw "dat/parper-proposed-time.dat"    withcolor blue;
        
        gridding;
       
        gdraw "dat/parper-fastNDS-time.dat"     withcolor red;
        gdraw "dat/parper-ENLU-time.dat"        withcolor green;
        gdraw "dat/parper-proposed-time.dat"    withcolor blue;

        gdraw "dat/parper-fastNDS-time.dat"     plot colPoint(red);
        glabel.ulft(btex Fast NDS etex, 3);
        gdraw "dat/parper-ENLU-time.dat"        plot colPoint(green);
        glabel.ulft(btex ENLU etex, 1);
        gdraw "dat/parper-proposed-time.dat"    plot colPoint(blue);
        glabel.lrt(btex Proposed etex, 3);
    endgraph;
endfig;

verbatimtex
\end{document}
etex

end.

