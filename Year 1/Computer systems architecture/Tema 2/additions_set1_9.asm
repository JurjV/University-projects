bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ;a - byte, b - word, c - double word, d - qword
    a db 8
    b dw 12
    c dd 50
    d dq 2000
    

; our code starts here
segment code use32 class=code
    start:
        ;(d+d-b)+(c-a)+d
        ;d= edx:edx 
        mov eax,[d+0] ;eax=lower part of number
        mov edx,[d+4] ;edx=higher part of number
        add eax,eax ;eax=eax+eax
        adc edx,edx ;edx=edx+edx
        sub eax,[b]
        mov ecx,0
        sub edx,ecx ; EDX:EAX = (d+d-b)
        mov ecx,0
        mov ecx,[c]
        mov ebx,0
        mov bl,[a]
        sub ecx,ebx
        mov ebx,0
        clc
        add eax,ecx
        adc edx,0 ; EDX:EAX = (d+d-b)+(c-a)
        mov ecx,[d+0]
        mov ebx,[d+4]
        add eax,ecx
        adc edx,ebx ; EDX:EAX =(d+d-b)+(c-a)+d
        
        
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
