# A-Maze-Ing
Javā veidots Labirintu ģenerātors un risinātājs priekš 2021. gada RTU programmēšanas konkursa.

Pieņem lietotāja ievadi, vai ļauj automātiski izģenerēt labirintu, ko risināt. Attēlo labirinta masīvu
ar tiešām vērtībām, vai izmantojot bloku objektus vizualizācijai.

## Ģenerēšana
Automātiski izģenerē labirintus pēc 2 metodēm:
1. Nejauši novietojot blokus masīvā
2. ???

## Risināšana
Automātiski atrisina labirintus izmantojot 3 metodes:
1. Nejauši ejot visos virzienos līdz atrodam beigas
2. Nejauši iet cauri labirintam, strupceļa gadījumā aizverot iepriekšējo krustojumu ciet un sākot no sākuma.
3. Nejauši ejot cauri labirintam, atceroties kur ir iets un visus redzētos krustojumus. Sastopot bezizeju, 
ejam atpakaļ uz iepriekšējo krustojumu. 

### Lietas, kas vēl jāizdara
- [ ] Automātiskie testi.
- [ ] Jāpāriet pāri SolveClass funkcijām, iespējams tās var padarīt smukākas.
- [ ] Jauns ģenerātors priekš labirintiem.
- [ ] Iespējams izmantot vēl vienu risinājuma metodi (?!)
- [x] Salabot RealWorldSolve printēšanu

Licencēts zem MIT licenses, vairāk informācijas LICENSE failā.