verbatimtex
%&latex
\documentclass{article}
\usepackage[utf8]{inputenc}
\usepackage[T2A]{fontenc}
\usepackage[english,russian]{babel}

\begin{document}
etex

numeric u; u := 1cm;

def rectangle(expr baseX, baseY, width, height) =
    draw (baseX, baseY)
       --(baseX, baseY + height)
       --(baseX + width, baseY + height)
       --(baseX + width, baseY)
       --cycle;
enddef;

def circleLabelC(expr center, diameter, txt, clr) =
	fill fullcircle scaled diameter shifted center withcolor clr;
	draw fullcircle scaled diameter shifted center;
	label(txt, center);
enddef;

def circleLabel(expr center, diameter, txt) =
    circleLabelC(center, diameter, txt, white);
enddef;

def rectLabel(expr center, width, height, txt) =
    fill (center+(-width/2,-height/2))
       --(center+( width/2,-height/2))
       --(center+( width/2, height/2))
       --(center+(-width/2, height/2))
       --cycle withcolor white;
    draw (center+(-width/2,-height/2))
       --(center+( width/2,-height/2))
       --(center+( width/2, height/2))
       --(center+(-width/2, height/2))
       --cycle;
    label(txt, center);
enddef;

vardef xcenter(expr bx, by, sz, xx, yy) =
    (bx + sz * xx, by + sz * yy)
enddef;

def coordLabel(expr bx, by, sz, dd, xx, yy, clr) =
    circleLabelC(xcenter(bx, by, sz, xx, yy), dd, decimal(xx) & ";" & decimal(yy), clr);
enddef;

beginfig(1)
	numeric globalGap; globalGap := 0.5u;
	numeric sideStrut; sideStrut := 0.2u;

    pickup pencircle scaled 1pt;

	%======= Layers ========

	numeric leftHeadH; leftHeadH := 0.9u;
	numeric leftHeadW; leftHeadW := 9u;
	numeric leftHeadX; leftHeadX := 0u;
	numeric leftHeadY; leftHeadY := 0u;
	
	numeric leftHeadCount; leftHeadCount := 8;
	numeric leftHeadHGap;  leftHeadHGap := sideStrut;
	numeric leftHeadVGap;  leftHeadVGap := sideStrut;
	numeric leftHeadHSize; leftHeadHSize := (leftHeadW - (leftHeadCount + 1) * leftHeadHGap) / leftHeadCount;
	numeric leftHeadVSize; leftHeadVSize := leftHeadH - 2 * leftHeadVGap;
	
	rectangle(leftHeadX, leftHeadY, leftHeadW, leftHeadH);
	
	for i := 0 upto (leftHeadCount - 1):
		elemX := leftHeadX + leftHeadHGap + i * (leftHeadHSize + leftHeadHGap);
		elemY := leftHeadY + leftHeadVGap;
		if i = 1:
		    pickup pencircle scaled 2pt;
		else:
		    pickup pencircle scaled 1pt;
		fi;
		rectangle(elemX, elemY, leftHeadHSize, leftHeadVSize);
		label (decimal(i), (elemX + 0.5leftHeadHSize, elemY + 0.5leftHeadVSize));
	endfor;
	
	%======= Single layer on 2D ======
	
	numeric leftBotH; leftBotH := 5u;
	numeric leftBotW; leftBotW := leftHeadW;
	numeric leftBotX; leftBotX := leftHeadX;
	numeric leftBotY; leftBotY := leftHeadY - leftBotH - globalGap;

    rectangle(leftBotX, leftBotY, leftBotW, leftBotH);
    
    numeric coordZeroX; coordZeroX := leftBotX + sideStrut;
    numeric coordZeroY; coordZeroY := leftBotY + sideStrut;
    numeric coordW; coordW := leftBotW - 2 * sideStrut;
    numeric coordH; coordH := leftBotH - 2 * sideStrut;
    numeric coordOne; coordOne := coordW / 11;
    
    drawarrow (coordZeroX, coordZeroY)--(coordZeroX, coordZeroY + coordH);
    drawarrow (coordZeroX, coordZeroY)--(coordZeroX + coordW, coordZeroY);
	
	for x := 1 upto 10:
	    draw (coordZeroX + coordOne * x, coordZeroY)
	       --(coordZeroX + coordOne * x, coordZeroY + coordH) dashed evenly;
	endfor;
	
	for y := 1 upto 5:
	    draw (coordZeroX, coordOne * y + coordZeroY)
	       --(coordZeroX + coordW, coordOne * y + coordZeroY) dashed evenly;
	endfor;
	
	numeric clScale; clScale := coordOne;

	draw xcenter(coordZeroX, coordZeroY, coordOne, 1, 2)
	   --xcenter(coordZeroX, coordZeroY, coordOne, 2, 1);
	
    coordLabel(coordZeroX, coordZeroY, coordOne, clScale, 1, 2, white);	
    coordLabel(coordZeroX, coordZeroY, coordOne, clScale, 2, 1, white);	

    pickup pencircle scaled 2pt;

    draw (leftHeadX + 2 * leftHeadHGap + 1.5 * leftHeadHSize, leftHeadY + leftHeadVGap)
       --xcenter(coordZeroX, coordZeroY, coordOne, 1, 5) dashed evenly;
    draw xcenter(coordZeroX, coordZeroY, coordOne, 8, 1)
       --(leftBotX + leftBotW, leftBotY + 0.45leftBotH) dashed evenly;

	color selcol; selcol := 0.85white;
	
	draw xcenter(coordZeroX, coordZeroY, coordOne, 1, 5)
	   --xcenter(coordZeroX, coordZeroY, coordOne, 2, 4)
	   --xcenter(coordZeroX, coordZeroY, coordOne, 4, 3)
	   --xcenter(coordZeroX, coordZeroY, coordOne, 5, 2)
	   --xcenter(coordZeroX, coordZeroY, coordOne, 8, 1);
	
	coordLabel(coordZeroX, coordZeroY, coordOne, clScale, 1, 5, selcol);
	coordLabel(coordZeroX, coordZeroY, coordOne, clScale, 2, 4, selcol);
	coordLabel(coordZeroX, coordZeroY, coordOne, clScale, 4, 3, selcol);
	coordLabel(coordZeroX, coordZeroY, coordOne, clScale, 5, 2, selcol);
    coordLabel(coordZeroX, coordZeroY, coordOne, clScale, 8, 1, selcol);

    pickup pencircle scaled 1pt;

    draw xcenter(coordZeroX, coordZeroY, coordOne, 3, 5)
       --xcenter(coordZeroX, coordZeroY, coordOne, 5, 4)
       --xcenter(coordZeroX, coordZeroY, coordOne, 6, 3)
       --xcenter(coordZeroX, coordZeroY, coordOne, 10, 1);

    coordLabel(coordZeroX, coordZeroY, coordOne, clScale, 3, 5, white);	
    coordLabel(coordZeroX, coordZeroY, coordOne, clScale, 5, 4, white);	
    coordLabel(coordZeroX, coordZeroY, coordOne, clScale, 6, 3, white);	
    coordLabel(coordZeroX, coordZeroY, coordOne, clScale, 10, 1, white);	
	
    draw xcenter(coordZeroX, coordZeroY, coordOne, 6, 5)
       --xcenter(coordZeroX, coordZeroY, coordOne, 9, 3);

    coordLabel(coordZeroX, coordZeroY, coordOne, clScale, 6, 5, white);	
    coordLabel(coordZeroX, coordZeroY, coordOne, clScale, 9, 3, white);	
	
	%======= Layers as Tree =========
	
	numeric rightHeadH; rightHeadH := (leftHeadH + leftBotH) / 2;
	numeric rightHeadW; rightHeadW := 9u;
	numeric rightHeadX; rightHeadX := leftHeadX + leftHeadW + globalGap * 2;
	numeric rightHeadY; rightHeadY := leftBotY + rightHeadH + globalGap;
	
	draw (leftHeadX + leftHeadW, leftHeadY + leftHeadH / 2)
	   --(rightHeadX, leftHeadY + leftHeadH / 2) dashed evenly;

    rectangle(rightHeadX, rightHeadY, rightHeadW, rightHeadH);	
	
	numeric rightHeadDX; rightHeadDX := rightHeadW / 9;
	numeric rightHeadDY; rightHeadDY := rightHeadH / 5;
	
	pair ltA; ltA := (rightHeadX + 1 * rightHeadDX, rightHeadY + 1 * rightHeadDY);
	pair ltB; ltB := (rightHeadX + 2 * rightHeadDX, rightHeadY + 3 * rightHeadDY);
	pair ltC; ltC := (rightHeadX + 3 * rightHeadDX, rightHeadY + 1 * rightHeadDY);
	pair ltD; ltD := (rightHeadX + 4 * rightHeadDX, rightHeadY + 4 * rightHeadDY);
	pair ltE; ltE := (rightHeadX + 5 * rightHeadDX, rightHeadY + 2 * rightHeadDY);
	pair ltF; ltF := (rightHeadX + 6 * rightHeadDX, rightHeadY + 3 * rightHeadDY);
	pair ltG; ltG := (rightHeadX + 7 * rightHeadDX, rightHeadY + 1 * rightHeadDY);
	pair ltH; ltH := (rightHeadX + 8 * rightHeadDX, rightHeadY + 2 * rightHeadDY);

	draw ltA--ltB;
	draw ltB--ltC;
	draw ltB--ltD;
	draw ltD--ltF;
	draw ltF--ltE;
	draw ltF--ltH;
	draw ltH--ltG;
	
	numeric ltR; ltR := rightHeadDY;

    rectLabel(ltA, leftHeadHSize, leftHeadVSize, btex 0 (1) etex);	
    rectLabel(ltB, leftHeadHSize, leftHeadVSize, btex 1 (2) etex);	
    rectLabel(ltC, leftHeadHSize, leftHeadVSize, btex 2 (1) etex);	
    rectLabel(ltD, leftHeadHSize, leftHeadVSize, btex 3 (8) etex);	
    rectLabel(ltE, leftHeadHSize, leftHeadVSize, btex 5 (1) etex);	
    rectLabel(ltF, leftHeadHSize, leftHeadVSize, btex 4 (4) etex);	
    rectLabel(ltG, leftHeadHSize, leftHeadVSize, btex 7 (1) etex);	
    rectLabel(ltH, leftHeadHSize, leftHeadVSize, btex 6 (2) etex);	

	%======= Single layer as Tree ============

	numeric rightBotH; rightBotH := rightHeadH;
	numeric rightBotW; rightBotW := rightHeadW;
	numeric rightBotX; rightBotX := rightHeadX;
	numeric rightBotY; rightBotY := leftBotY;

    pickup pencircle scaled 2pt;
	draw (leftBotX + leftBotW, leftBotY + 0.45leftBotH)
	   --(rightBotX, leftBotY + 0.45leftBotH) dashed evenly;

    rectangle(rightBotX, rightBotY, rightBotW, rightBotH);
    pickup pencircle scaled 1pt;

	numeric rightBotDX; rightBotDX := rightBotW / 6;
	numeric rightBotDY; rightBotDY := rightBotH / 4;
	
	pair lbA; lbA := (rightBotX + 1 * rightBotDX, rightBotY + 1 * rightBotDY);
	pair lbB; lbB := (rightBotX + 2 * rightBotDX, rightBotY + 2 * rightBotDY);
	pair lbC; lbC := (rightBotX + 3 * rightBotDX, rightBotY + 1 * rightBotDY);
	pair lbD; lbD := (rightBotX + 4 * rightBotDX, rightBotY + 3 * rightBotDY);
	pair lbE; lbE := (rightBotX + 5 * rightBotDX, rightBotY + 2 * rightBotDY);

    draw lbA--lbB;
    draw lbB--lbC;
    draw lbB--lbD;
    draw lbD--lbE;

	numeric lbR; lbR := rightBotDY;

    circleLabelC(lbA, lbR, "1;5", selcol);	
    circleLabelC(lbB, lbR, "2;4", selcol);	
    circleLabelC(lbC, lbR, "4;3", selcol);	
    circleLabelC(lbD, lbR, "5;2", selcol);	
    circleLabelC(lbE, lbR, "8;1", selcol);	
endfig;

def drawFigTwo(expr O, isBefore) =
    pickup pencircle scaled 1.5pt;
    numeric D; D := 1u;
    numeric W; W := 10;
    numeric H; H := 10;
    
    drawarrow O--(O + (D * (W + 1), 0u));
    drawarrow O--(O + (0u, D * (H + 1)));
    
    pickup pencircle scaled 0.5pt;
    for i := 1 upto W:
        draw (O + (D * i, 0u))--(O + (D * i, D * (H + 0.5))) dashed evenly;
    endfor;
    for i := 1 upto H:
        draw (O + (0u, D * i))--(O + (D * (W + 0.5), D * i)) dashed evenly;
    endfor;
    pickup pencircle scaled 1.5pt;
    
    numeric R; R := 0.7u;

    pair pAA; pAA := O + (D * 1, D * 4);
    pair pAB; pAB := O + (D * 2, D * 2);
    pair pAC; pAC := O + (D * 5, D * 1);
        
    pair pBA; pBA := O + (D * 1, D * 7);
    pair pBB; pBB := O + (D * 2, D * 6);
    pair pBC; pBC := O + (D * 4, D * 5);
    pair pBD; pBD := O + (D * 5, D * 4);
    pair pBE; pBE := O + (D * 7, D * 3);
    pair pBF; pBF := O + (D * 10, D * 2);

    pair pCA; pCA := O + (D * 3, D * 10);
    pair pCB; pCB := O + (D * 4, D * 8);
    pair pCC; pCC := O + (D * 5, D * 6);
    pair pCD; pCD := O + (D * 6, D * 5);
    pair pCE; pCE := O + (D * 9, D * 3);

    pair pDA; pDA := O + (D * 5, D * 9);
    pair pDB; pDB := O + (D * 7, D * 8);
    pair pDC; pDC := O + (D * 8, D * 7);

    pair new; new := O + (D * 3, D * 3);

    draw pAA--pAB--pAC;
    draw pDA--pDB--pDC;

    if isBefore:
        draw pBA--pBB--pBC--pBD--pBE--pBF;
        draw pCA--pCB--pCC--pCD--pCE;
    else:
        draw pBA--pBB--new--pBF;
        draw pCA--pBC--pBD--pBE;
        draw pCB--pCC--pCD--pCE;
    fi;

    circleLabelC(new, R, "", 0.1white);
    
    circleLabelC(pAA, R, "", white);
    circleLabelC(pAB, R, "", white);
    circleLabelC(pAC, R, "", white);
    
    circleLabelC(pBA, R, "", white);
    circleLabelC(pBB, R, "", white);
    circleLabelC(pBC, R, "", 0.5white);
    circleLabelC(pBD, R, "", 0.5white);
    circleLabelC(pBE, R, "", 0.5white);
    circleLabelC(pBF, R, "", white);

    circleLabelC(pCA, R, "", white);
    circleLabelC(pCB, R, "", 0.8white);
    circleLabelC(pCC, R, "", 0.8white);
    circleLabelC(pCD, R, "", 0.8white);
    circleLabelC(pCE, R, "", 0.8white);
    
    circleLabelC(pDA, R, "", white);
    circleLabelC(pDB, R, "", white);
    circleLabelC(pDC, R, "", white);
enddef;

beginfig(2)
    drawFigTwo((0u, 0u), true);
    drawarrow (11u, 4u)--(12.5u, 4u);
    drawarrow (11u, 5u)--(12.5u, 5u);
    drawarrow (11u, 6u)--(12.5u, 6u);
    drawFigTwo((13u, 0u), false);
endfig;

beginfig(3)
    pickup pencircle scaled 1.5pt;
    pair O; O := (0u, 0u);
    numeric D; D := 1u;
    numeric W; W := 10;
    numeric H; H := 10;

    pickup pencircle scaled 0.5pt;
    for i := -W upto W / 2 + 2:
        draw (O + (D * i, -D * (H + 0.5)))--(O + (D * i, D * (H / 2 + 2.5))) dashed evenly;
    endfor;
    for i := -H upto H / 2 + 2:
        draw (O + (-D * (W + 0.5), D * i))--(O + (D * (W / 2 + 2.5), D * i)) dashed evenly;
    endfor;
    pickup pencircle scaled 1.5pt;

    drawarrow (O - (D * (W + 1), 0u))--(O + (D * (W / 2 + 3), 0u));
    drawarrow (O - (0u, D * (H + 1)))--(O + (0u, D * (H / 2 + 3)));

    numeric R; R := 0.4u;

    circleLabelC(O + (D * (-2), D * ( 6)), R, "", black);
    circleLabelC(O + (D * (-1), D * ( 5)), R, "", black);
    circleLabelC(O + (D * ( 0), D * ( 4)), R, "", black);
    circleLabelC(O + (D * ( 1), D * ( 3)), R, "", black);
    circleLabelC(O + (D * ( 2), D * ( 2)), R, "", black);
    circleLabelC(O + (D * ( 3), D * ( 1)), R, "", black);
    circleLabelC(O + (D * ( 4), D * ( 0)), R, "", black);
    circleLabelC(O + (D * ( 5), D * (-1)), R, "", black);

    circleLabelC(O + (D * (-3), D * ( 5)), R, "", black);
    circleLabelC(O + (D * (-2), D * ( 4)), R, "", black);
    circleLabelC(O + (D * (-1), D * ( 3)), R, "", black);
    circleLabelC(O + (D * ( 0), D * ( 2)), R, "", black);
    circleLabelC(O + (D * ( 1), D * ( 1)), R, "", black);
    circleLabelC(O + (D * ( 2), D * ( 0)), R, "", black);
    circleLabelC(O + (D * ( 3), D * (-1)), R, "", black);
    circleLabelC(O + (D * ( 4), D * (-2)), R, "", black);

    circleLabelC(O + (D * (-1), D * (-6)), R, "", black);
    circleLabelC(O + (D * (-6), D * (-1)), R, "", black);
    circleLabelC(O + (D * (-2), D * (-7)), R, "", black);
    circleLabelC(O + (D * (-7), D * (-2)), R, "", black);
    circleLabelC(O + (D * (-3), D * (-8)), R, "", black);
    circleLabelC(O + (D * (-8), D * (-3)), R, "", black);
    circleLabelC(O + (D * (-4), D * (-9)), R, "", black);
    circleLabelC(O + (D * (-9), D * (-4)), R, "", black);
endfig;

verbatimtex
\end{document}
etex

end.
