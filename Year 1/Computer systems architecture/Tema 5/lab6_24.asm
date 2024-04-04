bits 32 ; Being given a string of doublewords, build another string of doublewords which will include only the doublewords from the given string which have an even number of bits with the value 1.

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    s dd 12345671h, 1A2C314Dh, 98FCD1D76h, 1278312Bh
    len equ ($-s)/4 ; the length of the string,in doublewords
    rez times len dd 0
    isolate db 16
    two db 2
    
    

; our code starts here
segment code use32 class=code
    start:
        mov ecx,len
        mov esi,s ; in eds:esi we will store the FAR address of the string "s"
        
        mov dh,0 ; counts how many have even 1 bits
        cld
        repeat1:
            lodsd ; parse from left to right
            push eax
            mov ebx,eax
            mov eax,0
            mov dl,0 ; counts the 1 bits
            repeat2:
                mov al,bl
                div byte[isolate] ; we isolate the lower bit
                mov al,ah
                mov ah,0
            
                cmp al,1
                jne sari
                    inc dl
                sari:
                shr ebx,4
                cmp ebx,0
                jne repeat2
            mov al,dl
            div byte[two]
            cmp ah,0
            jne sari2
                inc dh
                push eax
            sari2:
            pop eax
        loop repeat1
        mov ecx,0
        mov cl,dh
        create:
            pop eax
            mov [rez+4*(ecx-1)],eax
            loop create
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
