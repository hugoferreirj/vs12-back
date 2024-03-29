SELECT * FROM PESSOA p CROSS JOIN CONTATO c;

SELECT * FROM PESSOA p JOIN CONTATO c ON C.ID_PESSOA = P.ID_PESSOA;

SELECT * FROM PESSOA p 
JOIN PESSOA_X_PESSOA_ENDERECO pxpe ON PXPE.ID_PESSOA = P.ID_PESSOA 
JOIN ENDERECO_PESSOA ep ON PXPE.ID_ENDERECO = EP.ID_ENDERECO;

SELECT * FROM PESSOA p 
JOIN CONTATO c ON C.ID_PESSOA = P.ID_PESSOA
JOIN PESSOA_X_PESSOA_ENDERECO pxpe ON PXPE.ID_PESSOA = P.ID_PESSOA 
JOIN ENDERECO_PESSOA ep ON PXPE.ID_ENDERECO = EP.ID_ENDERECO;

SELECT * FROM PESSOA p LEFT JOIN CONTATO c ON C.ID_PESSOA = P.ID_PESSOA;

SELECT * FROM PESSOA p 
LEFT JOIN PESSOA_X_PESSOA_ENDERECO pxpe ON PXPE.ID_PESSOA = P.ID_PESSOA 
LEFT JOIN ENDERECO_PESSOA ep ON PXPE.ID_ENDERECO = EP.ID_ENDERECO;

SELECT * FROM PESSOA p 
LEFT JOIN CONTATO c ON C.ID_PESSOA = P.ID_PESSOA
LEFT JOIN PESSOA_X_PESSOA_ENDERECO pxpe ON PXPE.ID_PESSOA = P.ID_PESSOA 
LEFT JOIN ENDERECO_PESSOA ep ON PXPE.ID_ENDERECO = EP.ID_ENDERECO;