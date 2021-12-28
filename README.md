# A-Maze-Ing
Labirintu ģenerators un risinātājs priekš 2021. gada RTU programmēšanas konkursa.

Pieņem lietotāja ievadi, vai ļauj automātiski izģenerēt labirintu, ko risināt. Attēlo labirinta masīvu
ar tiešām vērtībām, vai izmantojot bloku objektus vizualizācijai.

## Ģenerēšana
Automātiski izģenerē labirintus pēc 2 metodēm:
1. Nejauši novietojot blokus masīvā
2. Izveidojot ceļu no sākuma punkta līdz beigām, piepildot pārējo izmantojot nejaušus blokus.

## Risināšana
Automātiski atrisina labirintus izmantojot 3 metodes:
1. Nejauši ejot visos virzienos līdz atrodam beigas
2. Nejauši iet cauri labirintam, atceroties jau ietos ceļus, ja ceļi nenoved līdz beigām, sākot no sākuma,
3. Izmantojot Pledge algoritmu priekš labirinta iziešanas - Sekojot labajai labirinta sienai.


Licencēts zem MIT licenses, vairāk informācijas LICENSE failā.