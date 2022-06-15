# Projekt na zajęcia z przedmiotu TAL:
## Generowanie danych
Dane można wgenerować uruchamiając aplikację dostępną w pakiecie ***tal.generator***.
Po wygenerowaniu w folderze *resources* pojawi się plik *generated.json*. Plik ten reprezentuje graf, 
który aplikacja przekształca następnie na macierz sąsiedztwa między gałęziami. Należy mieć na uwadze,
że kolejne elementy wektora kolorów które powstaną po wykonaniu algorytmu są zgodne z kolejnością
elementów własnie w pliku *generated.json*.

## Algorytm dokładny
Algorytm dokładny można uruchomić wykorzystując klasę Main z pakietu ***tal.exact***.
Kod będący przedmiotem badań znajduje się w pliku *Graph* począwszy od linii z komentarzem
wskazującym, że kod poniżej tego komentarza jest badany. Pozostały kod jest pomocniczy i służy
do przekształcenia grafu w postaci *.json* do macierzy sąsiedztwa między krawędziami.
Przy badaniach zakładamy bowiem, że właśnie taka macierz sąsiedztwa zostanie zadana na wejściu.


## Algorytm heurystyczny
W.I.P