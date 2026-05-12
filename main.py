class Barca:
    def __init__(self):
        self.assentos = {}
        for i in range(60):
            for j in range(20):
                self.assentos[f"F{i+1:02d}A{j+1:02d}"] = False
        self.ocupacao = 0
    
    def extraiNumeros(self, assento:str) -> tuple:
        return (int(assento[1:3]), int(assento[4:6]))

    def validaAssento(self, assento:str) -> bool:
        if len(assento) != 6 or assento[0] != 'F' or assento[3] != 'A':
            return False
        fileira, lugar = self.extraiNumeros(assento)
        return False if fileira > 60 or lugar > 20 else True
        
    def preencheAssento(self, assento):
        self.assentos[assento] = True
        self.ocupacao += 1
        return 3

    def ocupaLugar(self, assento:str) -> int:
        if not self.validaAssento(assento):
            return 0
        if self.assentos[assento] == True:
            return 1
        
        fileira, lugar = self.extraiNumeros(assento)

        if self.ocupacao < 100 and not (1 <= fileira <= 20):
            return 2
        elif self.ocupacao < 200 and not (40 <= fileira <= 60):
            return 2

        if self.ocupacao < 1200:
            return self.preencheAssento(assento)
        
        return 1

    def ocupaLugarSemVerificacao(self, fila:int, assento: int):
        chave = f"F{fila:02d}A{assento:02d}"
        self.preencheAssento(chave)
        
