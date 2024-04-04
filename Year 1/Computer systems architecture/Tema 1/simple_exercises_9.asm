bits 32
global  start 

extern  exit
import  exit msvcrt.dll
        
segment  data use32 class=data 
    a db 3
    b db 4
    
segment  code use32 class=code 
start: 
	mov al,[a]
    mov ah,[b]
    mul ah
	
	push   dword 0 
	call   [exit] 