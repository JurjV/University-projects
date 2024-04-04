;a=10,b=20,c=30,d=40
bits 32
global  start 

extern  exit
import  exit msvcrt.dll
        
segment  data use32 class=data 
    a dw 10
    b dw 20
    c dw 30
    d dw 40
    
segment  code use32 class=code 
start: 
	mov eax,0
	mov ebx,0
    mov al,[a]
    sub al,[c]
    mov bl,[b]
    sub bl,[d]
    add al,bl
    
	
	push   dword 0 
	call   [exit] 
    