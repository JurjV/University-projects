 bits 32
global  start 

extern  exit
import  exit msvcrt.dll
        
segment  data use32 class=data 
    a db 2
    b db 15
    c db 2
    d db 12
    e dw 11
    f dw 21
    g dw 5
    h dw 8
    
segment  code use32 class=code 
start: 
	mov eax,0
	mov edx,0
    mov al,[a]
    sub al,[d]
    add al,[b]
    mov ah,2
    mul ah
    div byte [c]
    
    
	
	push   dword 0 
	call   [exit] 
    