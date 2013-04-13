package pt.ist.ave.jzx.Instructions;

public class JPM_NN extends Instruction {

	public JPM_NN(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		if (_cpu.getM_signF()) {
			_cpu.setM_pc16(_cpu.getM_memory().read16(_cpu.getM_pc16()));
		} else {
			_cpu.setM_pc16(_cpu.incinc16(_cpu.getM_pc16()));
		}
	}

	@Override
	public int incTstates() {
//		m_tstates += 10;
		return 10;
	}

}
