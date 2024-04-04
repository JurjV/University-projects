;   A list of doublewords is given. Starting from the low part of the doubleword, obtain the doubleword made of the high even bytes of the low words of ;      each doubleword from the given list. If there are not enough bytes, the remaining bytes of the doubleword will be filled with the byte FFh.

bits 32 

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data

     s DD 12345678h, 1A2C3C4Dh, 98FCDD76h, 12783A2Bh
     len equ ($-s)/4 ; the length of the string,in doublewords
     two db 2 ; variable for testing if the number is even

; our code starts here
segment code use32 class=code
    start:
        mov ebx,0
        sub ebx,1 ; initializing ebx with FFFF...
        
        mov edx,0 ;counts how many even numbers we have
        
        mov ecx,len
        mov esi,s ; in eds:esi we will store the FAR address of the string "s"
        cld ; parse from left to right
        repeat1:
            lodsd ; get every double word
            
            push ax
            mov eax,0
            pop ax
            shr eax,8 ; isolate the high byte of the low word of each doubleword
            
            div byte[two]
            cmp ah,0 
            jne sari
                mul byte[two]
                push ax ; push the word on the stack if the low byte is even
                inc edx ; increment if even byte found
            sari:
        loop repeat1
        
        mov ecx,edx ; we give ecx the number of even bytes found
        cmp ecx,0
        je Final ; if there are no even number we end the program
        repeat2:
            pop ax 
            shl ebx,8 
            mov bl,al   ; we put the even bytes found in ebx
        loop repeat2
        Final:
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
