package pt.ist.ave.jzx.instructions;

public class HALT extends Instruction {

	public HALT(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.dec16pc();
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
